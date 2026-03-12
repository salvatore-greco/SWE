package DomainModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudyRoomUnitTest {

    @Test
    public void testStudyRoomCommonConstructor() {
        StudyRoom aValidStudyRoom = new StudyRoom(0, 50);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new StudyRoom(1, -10);
        });
        assertEquals(0, aValidStudyRoom.getNumber(), "Actual room number doesn't match with expected");
    }
//
//    @Test
//    public void testStudyRoomIsAlwaysUnavailable() {
//
//    }

}
