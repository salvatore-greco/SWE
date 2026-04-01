package IntegrationTest.ORM;

import BusinessLogic.role;
import ORM.*;
import DomainModel.Card;

import DomainModel.LibraryUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardDAOIntegrationTest extends BaseIntegrationTest {
    private CardDAO cardDAO = new CardDAO();

    private UserDAO userDAO = new UserDAO();
    private LibraryUser user;

    @BeforeEach
    public void createLibraryUser(){
        userDAO = new UserDAO();
        UserDTO userDTO = new UserDTO(
                "prova3@email.com",
                "UtenteTest",
                "cognomeTest",
                role.libraryUser,
                "hashedPassword"
        );
        userDAO.insertUser(userDTO);

        user = new LibraryUser.LibraryUserBuilder()
                .setName("UtenteTest")
                .setSurname("cognomeTest")
                .setEmail("prova3@email.com")
                .build();
    }

    @Test
    public void CardDAO_setRequestedCard_returnsObject(){
        Card card = cardDAO.setRequestedCard(user);
        assertNotNull(card);
        assertTrue(card.getId() > 0);
    }

    @Test
    public void CardDAO_createCard_returnsObject(){
        Card card = cardDAO.createCard(user);
        assertNotNull(card);
        assertTrue(card.getId() > 0);
        assertNotNull(card.getExpirationDate());
    }

    @Test
    public void CardDAO_createCardFromRequest_returnsCard(){
        cardDAO.setRequestedCard(user);
        Card card = cardDAO.createCardFromRequest(user);
        assertTrue(card.getId() > 0);
        assertNotNull(card);
    }

    @Test
    public void CardDAO_createCardFromRequest_noRequest_returnsNull(){
        Card card = cardDAO.createCardFromRequest(user);
        assertNull(card);
    }

    @Test
    public void CardDAO_getCardByEmail_returnsCard(){
        Card card = cardDAO.getCardByEmail("email@email.com");
        // è una tessera valida (data.sql)
        assertNotNull(card);
        assertTrue(card.getId() > 0);
        assertNotNull(card.getIssueDate());
        assertNotNull(card.getExpirationDate());
    }

    @Test
    public void CardDAO_getCardByEmail_inexistentCard_returnNull(){
        Card card = cardDAO.getCardByEmail("neri.neri@email.com");
        // è una tessera valida (data.sql)
        assertNull(card);
    }
}
