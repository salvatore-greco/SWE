package ORM;

import BusinessLogic.role;

import Exception.data.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOUnitTest extends BaseDAOUnitTest{
    private UserDAO userDAO = new UserDAO();

    @Test
    public void UserDAO_getUserByEmail_returnsObject() {
        UserDTO user = new UserDTO(
                "prova@email.com",
                "Mario",
                "Rossi",
                role.valueOf("librarian"),
                "hashedPSW"
        );

        userDAO.insertUser(user);
        UserDTO retrievedUser = userDAO.getUserByEmail("prova@email.com");
        assertNotNull(retrievedUser);
        assertEquals("Mario", retrievedUser.getName());
    }

    @Test
    public void UserDAO_getUserByEmail_throwsExceptionIfNotExist() {
        assertThrows(UserNotFoundException.class, () -> userDAO.getUserByEmail("prova2@email.com"));
    }
}
