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
        EventRoom room = (EventRoom) roomDAO.getRoomByNumber(1);

        Event event = new Event.EventBuilder()
                .setName("EventoIntegrationTest")
                .setDescription("Descrizione")
                .setStartDate(LocalDateTime.now())
                .setEventDuration(Duration.ofHours(2))
                .setOrganizer(librarianController.getUser())
                .setPlace(room)
                .build();

        Integer id = eventDAO.setEvent(event);
        event.setId(id);
        return event;
    }

    //test del librarian controller
    @Test
    public void createEvent(){
        Event eventToCreate = createEventTest();
        assertNotNull(eventToCreate.getId());
    }

    @Test
    public void cancelEvent(){
        Event eventToDelete = createEventTest();

        assertDoesNotThrow(() -> librarianController.cancelEvent(eventToDelete));
    }

    @Test
    public void modifyEvent(){
        Event eventToModify = createEventTest();

        eventToModify.setName("EventoIntegrationTestModificato");
        eventToModify.setDescription("Descrizione modificata");

        assertDoesNotThrow(() -> librarianController.modifyEvent(eventToModify));
    }
}
