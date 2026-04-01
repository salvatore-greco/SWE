package IntegrationTest;

import BusinessLogic.*;
import ORM.*;
import DomainModel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryIntegrationTest extends BaseIntegrationTest {
    private static LibraryAdministratorController adminController;

    private static LibraryDAO libraryDAO;

    @BeforeAll
    public static void init(){
        libraryDAO = new LibraryDAO();

        LibraryAdministrator admin = new LibraryAdministrator("Admin", "admin@email.com", "Admin");

        Library library = libraryDAO.getLibraryByName("biblioteca libri belli");
        assertNotNull(library);

        admin.setLibraryManaged(library);

        adminController = new LibraryAdministratorController(admin);
    }

    @Test
    public void increaseBudget(){
        Library library = adminController.getUser().getLibraryManaged();
        int oldBudget = library.getBudget();
        int increaseAmount = 1000;

        assertDoesNotThrow(() -> adminController.increaseBudget(increaseAmount));
        Library updatedLibrary = libraryDAO.getLibraryByName(library.getName());
        assertEquals(oldBudget + increaseAmount, updatedLibrary.getBudget());
    }

    @Test
    public void increaseBudget_negativeIncrease_throws(){
        int negativeAmount = -500;
        var e = assertThrows(IllegalArgumentException.class, () -> adminController.increaseBudget(negativeAmount));
        assertEquals("Increase must be positive", e.getMessage());
    }
}
