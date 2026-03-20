package ORM;

import BusinessLogic.role;
import DomainModel.Card;

import DomainModel.LibraryUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardDAOUnitTest extends BaseDAOUnitTest{
    private CardDAO cardDAO = new CardDAO();

    private UserDAO userDAO = new UserDAO();

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

    @Test
    public void CardDAO_setReuqestedCard_returnsObject(){
        LibraryUser user = createLibraryUser();

        Card card = cardDAO.setRequestedCard(user);
        assertNotNull(card);
        assertTrue(card.getId() > 0);
    }


}
