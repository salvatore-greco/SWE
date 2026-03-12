package DomainModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventRoomUnitTest {

   /* @BeforeEach
    public void init(){
        EventRoom aValidEventRoom = new EventRoom(0, 50);
        ArrayList<Event> scheduledEvents = new ArrayList<>();
        scheduledEvents.add(new Event.EventBuilder()
                .setName("Nome")
                .setPlace(aValidEventRoom)
                .setDescription("lorem ipsum")
                .setStartDate(LocalDateTime.of(2026, 3, 1, 10, 0))
                .setEventDuration(Duration.of(1, ChronoUnit.HOURS))
                .setOrganizer(new Librarian("mario","prova@mail.com", "rossi"))
                .build()
        );
    }*/

    @Test
    public void testEventRoomCommonConstructor() {
        EventRoom aValidEventRoom = new EventRoom(0, 50);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new EventRoom(1, -10);
        });
        assertEquals(0, aValidEventRoom.getNumber(), "Actual room number doesn't match with expected");
    }

    @Test
    public void testInvalidTimeIntervalThrowsException() {
        EventRoom aValidEventRoom = new EventRoom(0, 50);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
           aValidEventRoom.isAvailable(null, Duration.of(10, ChronoUnit.SECONDS));
        });
        assertEquals("Invalid time interval", e.getMessage()); //Falliscono sempre dal solito if, lo controllo una volta sola
        assertThrows(IllegalArgumentException.class, () -> {
            aValidEventRoom.isAvailable(LocalDateTime.of(2026, 3, 1, 10, 0),null);
        });
//        Event eventA = new Event.EventBuilder()
//                .setName("Nome")
//                .setPlace(aValidEventRoom)
//                .setDescription("lorem ipsum")
//                .setStartDate(LocalDateTime.of(2026, 3, 1, 10, 0))
//                .setEventDuration(Duration.of(1, ChronoUnit.HOURS))
//                .setOrganizer(new Librarian("mario", "prova@mail.com", "rossi"))
//                .build();
//        aValidEventRoom.scheduleEvent(eventA);


    }
}
