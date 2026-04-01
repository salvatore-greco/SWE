package IntegrationTest;

import BusinessLogic.*;
import ORM.*;
import DomainModel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanIntegrationTest extends BaseIntegrationTest {

    private static LibrarianController librarianController;

    private static LibraryUserController libraryUserController;

    private static LibraryUserController userWithoutCardController;

    private static LibraryUserController userWithExpiredCardController;

    private static BookDAO bookDAO;



    @BeforeAll
    public static void init() {
        // del method hiding a junit non frega un cazzo.
        // viene chiamato prima BaseDAOUnitTest.init() e poi questo init()
        AuthService authService = new ConcreteAuthService(new UserDAO());
        librarianController = assertDoesNotThrow(() -> (LibrarianController) authService.login("prova@email.com", "bibliotecario"));
        libraryUserController = assertDoesNotThrow(() -> (LibraryUserController) authService.login("email@email.com", "libraryUser"));
        userWithoutCardController = assertDoesNotThrow(() -> (LibraryUserController) authService.login("neri.neri@email.com", "NeriNeri"));
        userWithExpiredCardController = assertDoesNotThrow(() -> (LibraryUserController) authService.login("gino.verdi@email.com", "gino"));
        bookDAO = new BookDAO();
    }

    @Test
    public void grantRequestedLoan() {
        Book bookToLoan = assertDoesNotThrow(() -> bookDAO.getBookByCode("A002"));
        Loan requestedLoan = assertDoesNotThrow(() -> libraryUserController.requestLoan(bookToLoan));
        assertFalse(requestedLoan.getGranted());
        assertFalse(requestedLoan.getEnded());
        assertDoesNotThrow(() -> librarianController.grantLoan(requestedLoan));
        Loan grantedLoan = new LoanDAO().getLoanByBookAndCard(bookToLoan.getCode(), requestedLoan.getCard().getId());
        assertTrue(grantedLoan.getGranted());
        assertFalse(grantedLoan.getEnded());
    }

    @Test
    public void grantLoan_notRequested(){
        Book bookToLoan = assertDoesNotThrow(() -> bookDAO.getBookByCode("A002"));
        Loan loan = new Loan(libraryUserController.getUser().getCard(), bookToLoan, false, false);
        assertDoesNotThrow(() -> librarianController.registerLoan(loan));
    }

    @Test
    public void loanTermination(){
        Loan loanToEnd = assertDoesNotThrow(() -> new LoanDAO().getLoanByBookAndCard("A001", 1)); //Loan presente nel db come dato iniziale (riscontrare con data.sql)
        assertNotNull(loanToEnd);
        assertTrue(loanToEnd.getGranted());
        assertFalse(loanToEnd.getEnded());
        librarianController.endLoan(loanToEnd);
        Loan loanEnded = assertDoesNotThrow(() -> new LoanDAO().getLoanByBookAndCard("A001", 1));
        assertTrue(loanEnded.getEnded());
    }

    @Test
    public void requestLoan_loanedBook_throws(){
        Book book = new BookDAO().getBookByCode("A001");
        var e = assertThrows(RuntimeException.class, () -> libraryUserController.requestLoan(book));
        assertEquals("Book is already loaned", e.getMessage());
    }
    @Test
    public void requestLoan_noCard_throws() {
        Book book = new BookDAO().getBookByCode("A002");
        var e = assertThrows(RuntimeException.class, () -> userWithoutCardController.requestLoan(book));
        assertEquals("User must have a card to request a loan", e.getMessage());
    }
    @Test
    public void requestLoan_expiredCard_throws() {
        Book book = new BookDAO().getBookByCode("A002");
        var e = assertThrows(RuntimeException.class, () -> userWithExpiredCardController.requestLoan(book));
        assertEquals("User card is expired", e.getMessage());
    }
}
