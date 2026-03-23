package IntegrationTest;

import IntegrationTest.ORM.BaseDAOUnitTest;
import BusinessLogic.*;
import ORM.*;
import DomainModel.*;

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
}
