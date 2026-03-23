package IntegrationTest;

import IntegrationTest.ORM.BaseDAOUnitTest;
import BusinessLogic.*;
import ORM.*;
import DomainModel.*;

import java.time.LocalDateTime;
import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventIntegrationTest extends BaseDAOUnitTest {

    private static LibrarianController librarianController;
    private static LibraryUserController libraryUserController;

    private static EventDAO eventDAO;
    private static RoomDAO roomDAO;

    @BeforeAll
    public static void init() {
        AuthService authService = new ConcreteAuthService(new UserDAO());
        librarianController = assertDoesNotThrow(() -> (LibrarianController) authService.login("prova@email.com", "bibliotecario"));
        libraryUserController = assertDoesNotThrow(() -> (LibraryUserController) authService.login("email@email.com", "libraryUser"));

        eventDAO = new EventDAO();
        roomDAO = new RoomDAO();
    }

    private Event createEventTest(){
        EventDTO event = new EventDTO(
                LocalDateTime.now().plusHours(1),
                Duration.ofHours(2),
                "EventoIntegrationTest",
                "Descrizione",
                1,
                librarianController.getUser().getEmail()
        );
        return librarianController.createEvent(event);
    }

    //test del librarian controller
    @Test
    public void createEvent(){
        Event eventToCreate = createEventTest();
        assertNotNull(eventToCreate.getId());
    }

    @Test
    public void createEvent_invalidRoom_throws(){
        EventDTO event = new EventDTO(
                LocalDateTime.now().plusHours(1),
                Duration.ofHours(2),
                "EventoIntegrationTest",
                "Descrizione",
                -1,
                librarianController.getUser().getEmail()
        );
        var e = assertThrows(IllegalArgumentException.class, () -> librarianController.createEvent(event));
        assertEquals("Room must exist", e.getMessage());
    }

    @Test
    public void createEvent_pastDate_throws() {
        EventDTO event = new EventDTO(
                LocalDateTime.now().minusHours(1),
                Duration.ofHours(1),
                "Evento",
                "Descrizione",
                1,
                librarianController.getUser().getEmail()
        );
        var e = assertThrows(IllegalArgumentException.class, () -> librarianController.createEvent(event));
        assertEquals("Event start date cannot be in the past", e.getMessage());
    }

    @Test
    public void createEvent_negativeDuration_throws() {
        EventDTO event = new EventDTO(
                LocalDateTime.now().plusHours(1),
                Duration.ofHours(-1),
                "Evento",
                "Descrizione",
                1,
                librarianController.getUser().getEmail()
        );
        var e = assertThrows(IllegalArgumentException.class, () -> librarianController.createEvent(event));
        assertEquals("Event duration must be positive", e.getMessage());
    }

    @Test
    public void createEvent_StudyRoom_throws(){
        EventDTO event = new EventDTO(
                LocalDateTime.now().plusHours(1),
                Duration.ofHours(1),
                "Evento",
                "Descrizione",
                2, //la stanza 2 è una StudyRoom, riscontrare con data.sql
                librarianController.getUser().getEmail()
        );
        var e = assertThrows(IllegalArgumentException.class, () -> librarianController.createEvent(event));
        assertEquals("Room must be an Event Room", e.getMessage());
    }

    @Test
    public void cancelEvent(){
        Event eventToDelete = createEventTest();
        assertDoesNotThrow(() -> librarianController.cancelEvent(eventToDelete));
    }

    @Test
    public void cancelEvent_notExisting_throws(){
        Event eventToDelete = createEventTest();
        eventToDelete.setId(-1);
        var e = assertThrows(RuntimeException.class, () -> librarianController.cancelEvent(eventToDelete));
        assertEquals("Event not found", e.getMessage());
    }

    @Test
    public void modifyEvent(){
        Event eventToModify = createEventTest();

        eventToModify.setName("EventoIntegrationTestModificato");
        eventToModify.setDescription("Descrizione modificata");

        assertDoesNotThrow(() -> librarianController.modifyEvent(eventToModify));
    }

    @Test
    public void modifyEvent_notExisting_throws(){
        Event eventToModify = createEventTest();
        eventToModify.setId(-1);
        var e = assertThrows(RuntimeException.class, () -> librarianController.modifyEvent(eventToModify));
        assertEquals("Event not found", e.getMessage());
    }
}
