package IntegrationTest;

import IntegrationTest.ORM.BaseDAOUnitTest;
import BusinessLogic.*;
import ORM.*;
import DomainModel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanIntegrationTest extends BaseDAOUnitTest {

    private static LibrarianController librarianController;

    private static LibraryUserController libraryUserController;

    private static BookDAO bookDAO;

    @BeforeAll
    public static void init() {
        // del method hiding a junit non frega un cazzo.
        // viene chiamato prima BaseDAOUnitTest.init() e poi questo init()
        AuthService authService = new ConcreteAuthService(new UserDAO());
        librarianController = assertDoesNotThrow(() -> (LibrarianController) authService.login("prova@email.com", "bibliotecario"));
        libraryUserController = assertDoesNotThrow(() -> (LibraryUserController) authService.login("email@email.com", "libraryUser"));
        bookDAO = new BookDAO();
    }

    @Test
    public void grantRequestedLoan() {
        Book bookToLoan = assertDoesNotThrow(() -> bookDAO.getBookByCode("A002"));
        Loan requestedLoan = assertDoesNotThrow(() -> libraryUserController.requestLoan(bookToLoan));
        assertFalse(requestedLoan.getGranted());
        assertFalse(requestedLoan.getEnded());
        assertDoesNotThrow(() -> librarianController.grantLoan(requestedLoan));
//        Loan grantedLoan = new LoanDAO().
    }


}
