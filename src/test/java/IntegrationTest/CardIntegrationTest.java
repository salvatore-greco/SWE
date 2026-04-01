package IntegrationTest;

import BusinessLogic.AuthService;
import BusinessLogic.ConcreteAuthService;
import BusinessLogic.LibrarianController;
import BusinessLogic.LibraryUserController;
import DomainModel.Card;
import IntegrationTest.ORM.BaseIntegrationTest;
import ORM.CardDAO;
import ORM.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardIntegrationTest extends BaseIntegrationTest {

    private static LibrarianController librarianController;

    private static LibraryUserController userWithoutCardController;

    private static LibraryUserController userWithCardController;

    @BeforeAll
    public static void init() {
        AuthService authService = new ConcreteAuthService(new UserDAO());
        librarianController = assertDoesNotThrow(() -> (LibrarianController) authService.login("prova@email.com", "bibliotecario"));
        userWithoutCardController = assertDoesNotThrow(() -> (LibraryUserController) authService.login("neri.neri@email.com", "NeriNeri"));
        userWithCardController = assertDoesNotThrow(() ->  (LibraryUserController) authService.login("email@email.com", "libraryUser"));
        assertNull(userWithoutCardController.getUser().getCard());
    }

    @Test
    public void createRequestedCard() {
        CardDAO cardDAO = new CardDAO();
        //request
        assertDoesNotThrow(() -> userWithoutCardController.requestCard());
        Card requestedCard = cardDAO.getCardByEmail(userWithoutCardController.getUser().getEmail());
        assertNotNull(requestedCard);
        assertNull(requestedCard.getIssueDate());
        assertNull(requestedCard.getExpirationDate());
        //create
        Card grantedCard = librarianController.createCard(userWithoutCardController.getUser());
        assertNotNull(grantedCard);
        assertNotNull(grantedCard.getIssueDate());
        assertNotNull(grantedCard.getExpirationDate());
    }

    @Test
    public void createCard(){
        init(); //devo pulire i side effect del precedente test (viene settata la card nel controller)
        assertNull(userWithoutCardController.getUser().getCard());
        librarianController.createCard(userWithoutCardController.getUser());
        Card grantedCard = librarianController.createCard(userWithoutCardController.getUser());
        assertNotNull(grantedCard);
        assertNotNull(grantedCard.getIssueDate());
        assertNotNull(grantedCard.getExpirationDate());
    }

    @Test
    public void requestCard_userWithCard_throws(){
        var e = assertThrows(RuntimeException.class, () -> userWithCardController.requestCard());
        assertEquals("User already have a card", e.getMessage());
    }

    @Test
    public void createCard_userWithCard_throws(){
        var e = assertThrows(RuntimeException.class, () -> librarianController.createCard(userWithCardController.getUser()));
        assertEquals("User already has a card", e.getMessage());
    }
}
