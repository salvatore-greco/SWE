package IntegrationTest.ORM;

import ORM.*;
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
                "prova3@email.com",
                "Utente",
                "Cognome",
                role.valueOf("libraryUser"),
                "hashedPassword");

        userDAO.insertUser(user);
        UserDTO retrievedUser = userDAO.getUserByEmail("prova3@email.com");
        assertNotNull(retrievedUser);
        assertEquals("Utente", retrievedUser.getName());
    }

    @Test
    public void UserDAO_getUserByEmail_throwsExceptionIfNotExist() {
        assertThrows(UserNotFoundException.class, () -> userDAO.getUserByEmail("prova2@email.com"));
    }

    @Test
    public void UserDAO_getLibraryManagedByAdmin_returnsObject() {
        Library library = userDAO.getLibraryManagedByAdmin("admin@email.com");
        assertNotNull(library);
    }

    @Test
    public void UserDAO_getLibraryManagedByAdmin_userNotAdmin_returnsNull() {
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
                "prova3@email.com",
                "Utente",
                "Cognome",
                role.valueOf("libraryUser"),
                "oldPassword");

        userDAO.insertUser(user);

        boolean updated = userDAO.updatePassword("prova3@email.com", "newHashedPassword");

        assertTrue(updated);
        UserDTO updatedUser = userDAO.getUserByEmail("prova3@email.com");
        assertEquals("newHashedPassword", updatedUser.getPassword());
    }

    @Test
    public void UserDAO_updatePassword_userNotFound(){
        boolean updated = userDAO.updatePassword("notFound@email.com", "newHashedPassword");
        assertFalse(updated);
    }

    @Test
    public void UserDAO_saveUser_returnsTrue(){
        UserDTO user = new UserDTO(
                "prova3@email.com",
                "Utente",
                "Cognome",
                role.valueOf("libraryUser"),
                "hashedPassword");

        userDAO.insertUser(user);

        UserDTO retrieved = userDAO.getUserByEmail("prova3@email.com");

        assertNotNull(retrieved);
        assertEquals("prova3@email.com", retrieved.getEmail());
        assertEquals("Utente", retrieved.getName());
    }
}
