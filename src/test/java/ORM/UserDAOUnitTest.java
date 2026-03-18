package ORM;

import BusinessLogic.role;

import DomainModel.Library;
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
                "hashedPassword"
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

    @Test
    public void UserDAO_getLibraryManagedByAdmin_returnsObject() {
        UserDTO user = new UserDTO(
                "admin@email.com",
                "Amministratore",
                "Accanito",
                role.valueOf("libraryAdministrator"),
                "hashedPassword");

        userDAO.insertUser(user);
        Library library = userDAO.getLibraryManagedByAdmin("admin@email.com");
        assertNotNull(library);
    }

    @Test
    public void UserDAO_getLibraryManagedByAdmin_userNotAdmin_returnsNull() {
        UserDTO user = new UserDTO(
                "prova@email.com",
                "Mario",
                "Rossi",
                role.valueOf("librarian"),
                "hashedPassword"
        );
        userDAO.insertUser(user);

        Library library = userDAO.getLibraryManagedByAdmin("prova@email.com");
        assertNull(library);
    }

    @Test
    public void UserDAO_getLibraryManagedByAdmin_userNotExist_returnsNull() {
        Library library = userDAO.getLibraryManagedByAdmin("notExist@email.com");
        assertNull(library);
    }

    @Test
    public void UserDAO_updatePassword_returnsTrue(){
        UserDTO user = new UserDTO(
                "admin@email.com",
                "Amministratore",
                "Accanito",
                role.valueOf("libraryAdministrator"),
                "oldPassword");

        userDAO.insertUser(user);

        boolean updated = userDAO.updatePassword("admin@email.com", "newHashedPassword");

        assertTrue(updated);
        UserDTO updatedUser = userDAO.getUserByEmail("admin@email.com");
        assertEquals("newHashedPassword", updatedUser.getHashedPassword());
    }

    @Test
    public void UserDAO_updatePassword_userNotFound(){
        boolean updated = userDAO.updatePassword("notFound@email.com", "newHashedPassword");
        assertFalse(updated);
    }
}
