package IntegrationTest.ORM;

import ORM.*;
import BusinessLogic.role;
import DomainModel.*;

import java.time.LocalDateTime;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOUnitTest extends BaseDAOUnitTest{
    private EventDAO eventDAO = new EventDAO();

    private UserDAO userDAO = new UserDAO();
    private RoomDAO roomDAO = new RoomDAO();

    private LibraryUser createLibraryUser(){
        UserDTO user = new UserDTO(
                "prova3@email.com",
                "UtenteTest",
                "cognomeTest",
                role.valueOf("libraryUser"),
                "hashedPassword"
        );
        userDAO.insertUser(user);

        LibraryUser libraryUser = new LibraryUser.LibraryUserBuilder()
                .setName("UtenteTest")
                .setSurname("cognomeTest")
                .setEmail("prova3@email.com")
                .build();

        return libraryUser;
    }

    private Librarian createLibrarian(){
        UserDTO user = new UserDTO(
                "librarianTest@email.com",
                "LibrarianTest",
                "cognomeTest",
                role.valueOf("librarian"),
                "hashedPassword"
        );
        userDAO.insertUser(user);

        Librarian librarian = new Librarian ("UtenteTest","librarianTest@email.com","cognomeTest");

        return librarian;
    }

    private EventRoom createEventRoom(){
        EventRoom eventRoom = (EventRoom) roomDAO.getRoomByNumber(1);
        return eventRoom;
    }

    private Event createEvent(){
        Librarian librarian = createLibrarian();
        EventRoom room = createEventRoom();

        LocalDateTime startDate = LocalDateTime.now();
        Duration eventDuration = Duration.ofHours(2);

        Event event = new Event.EventBuilder()
                .setName("EventoTest")
                .setDescription("DescrizioneTest")
                .setStartDate(startDate)
                .setEventDuration(eventDuration)
                .setOrganizer(librarian)
                .setPlace(room)
                .build();

        Integer eventId = eventDAO.setEvent(event);
        event.setId(eventId);

        return event;
    }

    @Test
    public void EventDAO_setEvent_returnsInteger(){
        Event event = createEvent();
        assertNotNull(event.getId());
    }

    @Test
    public void EventDAO_setEvent_invalidRoom_returnsNull(){
        Event event = createEvent();
        EventRoom invalidRoom = new EventRoom(-1, 100); // Numero di stanza non esistente
        event.setPlace(invalidRoom);

        Integer eventId = eventDAO.setEvent(event);
        event.setId(eventId);

        assertNull(eventId);
    }

    @Test
    public void EventDAO_addNewParticipant_returnsTrue(){
        Event event = createEvent();
        LibraryUser user = createLibraryUser();

        boolean result = eventDAO.addNewParticipant(event, user);
        assertTrue(result);
    }

    @Test
    public void EventDAO_addNewParticipant_notExistingUser_returnsFalse(){
        Event event = createEvent();
        LibraryUser user = new LibraryUser.LibraryUserBuilder()
                .setName("NonExistingUser")
                .setSurname("User")
                .setEmail("nulluser@email.com")
                .build();

        boolean result = eventDAO.addNewParticipant(event, user);
        assertFalse(result);
    }

    @Test
    public void EventDAO_removeParticipant_returnsTrue(){
        Event event = createEvent();
        LibraryUser user = createLibraryUser();

        eventDAO.addNewParticipant(event, user);

        boolean result = eventDAO.removeParticipant(event, user);
        assertTrue(result);
    }

    @Test
    public void EventDAO_removeParticipant_notAddedParticipant_returnsTrue(){
        Event event = createEvent();
        LibraryUser user = createLibraryUser();

        boolean result = eventDAO.removeParticipant(event, user);
        assertFalse(result);
    }

    @Test
    public void EventDAO_removeParticipant_notExistingParticipant_returnsFalse(){
        Event event = createEvent();
        LibraryUser user = new LibraryUser.LibraryUserBuilder()
                .setName("NonExistingUser")
                .setSurname("User")
                .setEmail("nulluser@email.com")
                .build();

        boolean result = eventDAO.removeParticipant(event, user);
        assertFalse(result);
    }

    @Test
    public void EventDAO_deleteEvent_returnsTrue(){
        Event event = createEvent();

        boolean result = eventDAO.deleteEvent(event);
        assertTrue(result);
    }

    @Test
    public void EventDAO_deleteEvent_notExistingEvent_returnsFalse(){
        Event event = new Event.EventBuilder()
                .setName("NonExistingEvent")
                .setDescription("DescrizioneTest")
                .setStartDate(LocalDateTime.now())
                .setEventDuration(Duration.ofHours(2))
                .setOrganizer(createLibrarian())
                .setPlace(createEventRoom())
                .build();

        event.setId(-1);

        boolean result = eventDAO.deleteEvent(event);
        assertFalse(result);
    }

    @Test
    public void EventDAO_editEvent_returnsTrue(){
        Event event = createEvent();

        event.setName("nome modificato");
        event.setDescription("descrizione modificata");

        boolean updated = eventDAO.editEvent(event);
        assertTrue(updated);
    }

    @Test
    public void EventDAO_editEvent_notExistingEvent_returnsFalse(){
        Event event = new Event.EventBuilder()
                .setName("NonExistingEvent")
                .setDescription("DescrizioneTest")
                .setStartDate(LocalDateTime.now())
                .setEventDuration(Duration.ofHours(2))
                .setOrganizer(createLibrarian())
                .setPlace(createEventRoom())
                .build();

        event.setId(-1);

        boolean result = eventDAO.editEvent(event);
        assertFalse(result);
    }
}
