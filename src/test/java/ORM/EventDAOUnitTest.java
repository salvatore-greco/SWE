package ORM;

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

        return event;
    }

    @Test
    public void EventDAO_addNewParticipant_returnsTrue(){
        Event event = createEvent();
        LibraryUser user = createLibraryUser();

        Integer eventId = eventDAO.setEvent(event);
        assertNotNull(eventId);

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

        Integer eventId = eventDAO.setEvent(event);
        event.setId(eventId);

        boolean result = eventDAO.addNewParticipant(event, user);
        assertFalse(result);
    }
}
