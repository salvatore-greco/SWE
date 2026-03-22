package UnitTest.DomainModel;

import DomainModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventRoomUnitTest {

    private EventRoom eventRoom;
    private Event eventA;

    @BeforeEach
    public void init() {
        eventRoom = new EventRoom(0, 50);

        eventA = new Event.EventBuilder()
                .setName("Nome")
                .setPlace(eventRoom)
                .setDescription("lorem ipsum")
                .setStartDate(LocalDateTime.of(2026, 3, 1, 10, 0))
                .setEventDuration(Duration.of(1, ChronoUnit.HOURS))
                .setOrganizer(new Librarian("mario", "prova@mail.com", "rossi"))
                .build();
    }

    @Test
    public void test_EventRoom_CommonConstructor() {
        EventRoom aValidEventRoom = new EventRoom(0, 50);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new EventRoom(1, -10);
        });
        assertEquals(0, aValidEventRoom.getNumber(), "Actual room number doesn't match with expected");
    }

    @Test
    public void test_EventRoom_isAvailableInvalidTimeInterval_ThrowsException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            eventRoom.isAvailable(null, Duration.of(10, ChronoUnit.SECONDS));
        });
        assertEquals("Invalid time interval", e.getMessage()); //Falliscono sempre dal solito if, lo controllo una volta sola
        assertThrows(IllegalArgumentException.class, () -> {
            eventRoom.isAvailable(LocalDateTime.of(2026, 3, 1, 10, 0), null);
        });
    }

    @Test
    public void test_EventRoom_schedulingValidEvent_doesNotThrow() {
        assertDoesNotThrow(() -> eventRoom.scheduleEvent(eventA));
    }

    @Test
    public void test_EventRoom_schedulingInvalidEvent_throws(){
        eventRoom.scheduleEvent(eventA); //Inserisco un evento valido (funziona per via del precedente test)

        //overlapping event. Rimetto il precedente
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> eventRoom.scheduleEvent(eventA));
        assertEquals("Room is not available", e.getMessage());
    }
}
