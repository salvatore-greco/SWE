package DomainModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class EventUnitTest {
    private Event event;

    private Librarian organizerA;
    private EventRoom eventRoomA;

    @BeforeEach
    public void init(){
        eventRoomA = new EventRoom(0, 50);
        organizerA = new Librarian("mario", "prova@mail.com", "rossi");

        event = new Event.EventBuilder()
                .setName("Nome")
                .setPlace(eventRoomA)
                .setDescription("lorem ipsum")
                .setStartDate(LocalDateTime.of(2026, 3, 1, 10, 0))
                .setEventDuration(Duration.of(1, ChronoUnit.HOURS))
                .setOrganizer(organizerA)
                .build();
    }

    @Test
    public void test_Event_CommonConstructor() {
        assertEquals("Nome", event.getName(), "Actual event name doesn't match expected");
        assertEquals("lorem ipsum", event.getDescription(), "Description mismatch");
        assertEquals(LocalDateTime.of(2026,3,1,10,0), event.getStartDate(), "Start date mismatch");
        assertEquals(Duration.ofHours(1), event.getEventDuration(), "Duration mismatch");
        assertEquals(eventRoomA, event.getPlace(), "Room mismatch");
        assertEquals(organizerA, event.getOrganizer(), "Organizer mismatch");
    }
}