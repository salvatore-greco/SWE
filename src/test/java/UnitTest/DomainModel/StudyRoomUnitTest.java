package UnitTest.DomainModel;

import DomainModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StudyRoomUnitTest {
    private StudyRoom studyRoom;
    LibraryUser user;

    @BeforeEach
    public void init() {
        ArrayList<LibraryUser> reservedSeats = new ArrayList<>();
        user = new LibraryUser.LibraryUserBuilder()
                .setName("mario")
                .setSurname("rossi")
                .setEmail("prova@mail.com")
                .setCard(1, LocalDate.now(), LocalDate.now().plusYears(3))
                .build();
        reservedSeats.add(user);
        studyRoom = new StudyRoom(1, 2, reservedSeats);
    }

    @Test
    public void testStudyRoomCommonConstructor() {
        StudyRoom aValidStudyRoom = new StudyRoom(0, 50);
        assertThrows(IllegalArgumentException.class, () -> new StudyRoom(1, -10));
        assertEquals(0, aValidStudyRoom.getNumber(), "Actual room number doesn't match with expected");
    }

    @Test
    public void StudyRoom_isAvailable_returnsAlwaysFalse() {
        assertFalse(studyRoom.isAvailable(
                LocalDateTime.of(2026, 1, 1, 11, 0),
                Duration.of(1, ChronoUnit.HOURS))
        );
    }

    @Test
    public void StudyRoom_reserveSeat_addUser() {
        LibraryUser otherUser = new LibraryUser.LibraryUserBuilder()
                .setName("luca")
                .setSurname("bianchi")
                .setEmail("luca@mail.com")
                .setCard(2, LocalDate.now(), LocalDate.now().plusYears(3))
                .build();
        studyRoom.reserveSeat(otherUser);
        assertTrue(studyRoom.getReservedSeats().contains(user));
        assertTrue(studyRoom.getReservedSeats().contains(otherUser));
        assertEquals(2, studyRoom.getReservedSeats().size());
    }

    @Test
    public void StudyRoom_reserveSeat_throwsDuplicateUser() {
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> studyRoom.reserveSeat(user));
        assertEquals("User already has a reserved seat in this room", e.getMessage());
    }

    @Test
    public void StudyRoom_reserveSeat_throwsRoomFull() {
        StudyRoom fullRoom = new StudyRoom(2, 1);
        fullRoom.reserveSeat(new LibraryUser.LibraryUserBuilder()
                .setName("luca")
                .setSurname("bianchi")
                .setEmail("luca@mail.com")
                .setCard(2, LocalDate.now(), LocalDate.now().plusYears(3))
                .build());
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> fullRoom.reserveSeat(user));
        assertEquals("No more seats available in this room", e.getMessage());
    }

    @Test
    public void StudyRoom_leaveSeat_removeUser() {
        studyRoom.leaveSeat(user);
        var resSeat = studyRoom.getReservedSeats();
        assertFalse(resSeat.contains(user));
        assertEquals(0, resSeat.size());
    }

    @Test
    public void StudyRoom_leaveSeat_throwsUserNotFound() {
        LibraryUser otherUser = new LibraryUser.LibraryUserBuilder()
                .setName("luca")
                .setSurname("bianchi")
                .setEmail("luca@mail.com")
                .setCard(2, LocalDate.now(), LocalDate.now().plusYears(3))
                .build();
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> studyRoom.leaveSeat(otherUser));
        assertEquals("User does not have a reserved seat in this room", e.getMessage());
    }
}
