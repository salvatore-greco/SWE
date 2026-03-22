package UnitTest.DomainModel;

import DomainModel.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RoomUnitTest {

    static class TestRoom extends Room{
        public TestRoom(int number, int seats) {
            super(number, seats);
        }

        @Override
        public boolean isAvailable(LocalDateTime start, Duration duration) {
            return true;
        }
    }

    @Test
    public void test_Room_validRoomCreation(){
        TestRoom room = new TestRoom(1, 20);

        assertEquals(1, room.getNumber());
        assertEquals(20, room.getSeats());
    }

    @Test
    public void test_Room_invalidSeatsNumber(){
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, ()-> new TestRoom(1, -10));
        assertEquals("Seats must be positive", e.getMessage());
    }
}