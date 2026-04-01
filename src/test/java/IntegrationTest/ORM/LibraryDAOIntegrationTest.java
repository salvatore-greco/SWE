package IntegrationTest.ORM;

import ORM.*;
import DomainModel.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryDAOIntegrationTest extends BaseIntegrationTest {

    @Test
    public void LibraryDAO_getLibraryByName_returnsObject() {
        LibraryDAO libraryDAO = new LibraryDAO();
        Library library = libraryDAO.getLibraryByName("biblioteca libri belli");
        assertEquals("biblioteca libri belli", library.getName());
        assertEquals(125000, library.getBudget());
    }

    @Test
    public void LibraryDAO_getLibraryByName_nullIfNotExist() {
        LibraryDAO libraryDAO = new LibraryDAO();
        Library library = libraryDAO.getLibraryByName("biblioteca che non esiste");
        assertNull(library);
    }

    @Test
    public void LibraryDAO_increaseBudget() {
        LibraryDAO libraryDAO = new LibraryDAO();
        assertTrue(libraryDAO.increaseBudgetOfLibrary("biblioteca libri belli", 126000));
        Library library = libraryDAO.getLibraryByName("biblioteca libri belli");
        assertEquals(126000, library.getBudget());
    }

    @Test
    public void LibraryDAO_increaseBudget_falseIfLibraryNotExist() {
        LibraryDAO libraryDAO = new LibraryDAO();
        assertFalse(libraryDAO.increaseBudgetOfLibrary("biblioteca che non esiste", 126000));
    }
}
