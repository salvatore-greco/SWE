package IntegrationTest.ORM;

import ORM.*;
import DomainModel.EventRoom;
import DomainModel.LibraryUser;
import DomainModel.StudyRoom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomDAOUnitTest extends BaseDAOUnitTest {

    @Test
//    @Order(1)
    public void RoomDAO_testAddSeatReservationStudyRoom() {
        StudyRoom room = new StudyRoom(2, 35);
        LocalDate cardDate = LocalDate.of(2026, 3, 17);
        LibraryUser user = new LibraryUser.LibraryUserBuilder()
                .setEmail("email@email.com")
                .setName("Luca")
                .setSurname("Bianchi")
                .setCard(1, cardDate, cardDate.plusYears(3))
                .build();
        RoomDAO roomDAO = new RoomDAO();
        assertTrue(roomDAO.addSeatReservationStudyRoom(room, user));
        //controllo che effettivamente i dati siano stati inseriti nel db
        try {
            Statement stmt = connDAO.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from reservation_study_room where \"user\" = 'email@email.com'");
            if (rs.next()) {
                assertEquals("email@email.com", rs.getString("user"));
                assertEquals(2, rs.getInt("room"));
            } else
                fail("Error getting from reservation_study_room, result set is empty or user isn't into that table");
            stmt.close();
        } catch (SQLException e) {
            try{connDAO.rollback();} catch (SQLException _) {};
            fail("DB access failed in RoomDAO_testAddSeatReservationStudyRoom");
        }
    }

    @Test
//    @Order(2)
    public void RoomDAO_testRemoveSeatReservationStudyRoom() {
        StudyRoom room = new StudyRoom(2, 35);
        LibraryUser user = new LibraryUser.LibraryUserBuilder()
                .setEmail("lampa.dario@email.com")
                .setName("Dario")
                .setSurname("Lampa")
                .setCard(2, null, null)
                .build();
        RoomDAO roomDAO = new RoomDAO();
        assertTrue(roomDAO.removeSeatReservationStudyRoom(room, user));
        try {
            Statement stmt = connDAO.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT FROM reservation_study_room where \"user\" = 'email@email.com'");
            if (rs.next())
                fail("User is still into table");
            else
                return;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("DB access failed in RoomDAO_testAddSeatReservationStudyRoom");
        }
    }

    @Test
//    @Order(3)
    public void RoomDAO_getStudyRoom_hasEveryReservation(){
        RoomDAO roomDAO = new RoomDAO();
        //questi magic numbers dipendono dai dati inseriti nel db di test -> controllare data.sql
        StudyRoom studyRoom = (StudyRoom) roomDAO.getRoomByNumber(5);
        assertEquals(5, studyRoom.getNumber());
        assertEquals(2, studyRoom.getSeats());
        var reservedSeat = studyRoom.getReservedSeats();
        assertEquals(2, reservedSeat.size());
        assertAll(() -> assertEquals("gino.verdi@email.com", reservedSeat.getFirst().getEmail()),
                () -> assertEquals("neri.neri@email.com", reservedSeat.getLast().getEmail()));
    }
    @Test
    public void RoomDAO_getStudyRoom_withNoReservation(){
        RoomDAO roomDAO = new RoomDAO();
        //questi magic numbers dipendono dai dati inseriti nel db di test -> controllare data.sql
        StudyRoom studyRoom = (StudyRoom) roomDAO.getRoomByNumber(4);
        assertEquals(4, studyRoom.getNumber());
        assertEquals(35, studyRoom.getSeats());
        var reservedSeat = studyRoom.getReservedSeats();
        assertTrue(reservedSeat.isEmpty());
    }

    @Test
    public void RoomDAO_getEventRoom_hasEveryEvents(){
        RoomDAO roomDAO = new RoomDAO();
        EventRoom eventRoom = (EventRoom) roomDAO.getRoomByNumber(1);
        assertEquals(1,eventRoom.getNumber());
        assertEquals(50, eventRoom.getSeats());
        var scheduledEvents = eventRoom.getScheduledEvents();
        assertEquals(2, scheduledEvents.size());
        assertEquals("presentazione libro aaa bbb", scheduledEvents.getFirst().getName());
        assertEquals("presentazione libro aaa bbb ccc", scheduledEvents.getLast().getName());
    }

    @Test
    public void RoomDao_getEventRoom_withNoEvents() {
        RoomDAO roomDAO = new RoomDAO();
        EventRoom eventRoom = (EventRoom) roomDAO.getRoomByNumber(3);
        assertEquals(3,eventRoom.getNumber());
        assertEquals(20, eventRoom.getSeats());
        var scheduledEvents = eventRoom.getScheduledEvents();
        assertTrue(scheduledEvents.isEmpty());
    }
}
