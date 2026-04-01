package IntegrationTest;

import BusinessLogic.AuthService;
import BusinessLogic.ConcreteAuthService;
import BusinessLogic.LibraryUserController;
import DomainModel.StudyRoom;
import ORM.RoomDAO;
import ORM.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudyRoomIntegrationTest extends BaseIntegrationTest {

    private static LibraryUserController libraryUserController;

    @BeforeAll
    public static void init() {
        AuthService authService = new ConcreteAuthService(new UserDAO());
        libraryUserController = assertDoesNotThrow(() -> (LibraryUserController) authService.login("email@email.com", "libraryUser"));
    }

    @Test
    public void reserveSeatStudyRoom_success() {
        RoomDAO roomDAO = new RoomDAO();
        StudyRoom studyRoom = (StudyRoom) roomDAO.getRoomByNumber(2);
        //Reserve seat su studyroom
        assertDoesNotThrow(() -> libraryUserController.reserveSeatStudyRoom(studyRoom));
        assertTrue(studyRoom.getReservedSeats().contains(libraryUserController.getUser()));
        //verifica che effettivamente ci sia nel database
        StudyRoom roomAfterInsertion = (StudyRoom) roomDAO.getRoomByNumber(2);
        assertTrue(roomAfterInsertion.getReservedSeats().contains(libraryUserController.getUser()));
    }


    @Test
    public void reserveSeatStudyRoom_roomFull_throws() {
        RoomDAO roomDAO = new RoomDAO();
        StudyRoom studyRoom = (StudyRoom) roomDAO.getRoomByNumber(5);
        var e = assertThrows(IllegalStateException.class, () -> libraryUserController.reserveSeatStudyRoom(studyRoom));
        assertEquals("No more seats available in this room", e.getMessage());
    }

    @Test
    public void reserveSeatStudyRoom_alreadyReserved_throws(){
        RoomDAO roomDAO = new RoomDAO();
        StudyRoom studyRoom = (StudyRoom) roomDAO.getRoomByNumber(2);
        assertDoesNotThrow(() -> libraryUserController.reserveSeatStudyRoom(studyRoom));

        var e = assertThrows(IllegalStateException.class, () -> libraryUserController.reserveSeatStudyRoom(studyRoom));
        assertEquals("User already has a reserved seat in this room", e.getMessage());
    }

    @Test
    public void leaveSeatStudyRoom_success() {
        RoomDAO roomDAO = new RoomDAO();
        StudyRoom studyRoom = (StudyRoom) roomDAO.getRoomByNumber(2);
        assertDoesNotThrow(() -> libraryUserController.reserveSeatStudyRoom(studyRoom));
        assertTrue(studyRoom.getReservedSeats().contains(libraryUserController.getUser()));

        assertDoesNotThrow(() -> libraryUserController.leaveSeatStudyRoom(studyRoom));
        assertFalse(studyRoom.getReservedSeats().contains(libraryUserController.getUser()));

        //verifica che effettivamente sia stato rimosso dal database
        StudyRoom roomAfterInsertion = (StudyRoom) roomDAO.getRoomByNumber(2);
        assertFalse(roomAfterInsertion.getReservedSeats().contains(libraryUserController.getUser()));
    }
    @Test
    public void leaveSeatStudyRoom_notReserved_throws() {
        RoomDAO roomDAO = new RoomDAO();
        StudyRoom studyRoom = (StudyRoom) roomDAO.getRoomByNumber(2);
        assertFalse(studyRoom.getReservedSeats().contains(libraryUserController.getUser()));

        var e = assertThrows(IllegalStateException.class, () -> libraryUserController.leaveSeatStudyRoom(studyRoom));
        assertEquals("User does not have a reserved seat in this room", e.getMessage());
    }
}
