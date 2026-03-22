package UnitTest.BusinessLogic;


import BusinessLogic.*;
import Exception.data.UserNotFoundException;
import ORM.UserDAO;
import ORM.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthUnitTest {

    private static UserDAO userDAO;

    @BeforeAll
    public static void init() {
        userDAO = mock(UserDAO.class);
        when(userDAO.getUserByEmail("library.user@email.com")).thenReturn(new UserDTO(
                "library.user@email.com",
                "Mario",
                "Rossi",
                role.libraryUser,
                BCrypt.hashpw("password", BCrypt.gensalt())
        ));
        when(userDAO.getUserByEmail("librarian@email.com")).thenReturn(new UserDTO(
                "librarian@email.com",
                "Luca",
                "Bianchi",
                role.librarian,
                BCrypt.hashpw("bibliotecario", BCrypt.gensalt())
        ));
        when(userDAO.getUserByEmail("admin@email.com")).thenReturn(new UserDTO(
                "admin@email.com",
                "Sara",
                "Verdi",
                role.libraryAdministrator,
                BCrypt.hashpw("admin", BCrypt.gensalt())
        ));
        when(userDAO.getUserByEmail("inesistente@email.com")).thenThrow(new UserNotFoundException("Wrong username or password"));
        when(userDAO.updatePassword(eq("library.user@email.com"), anyString())).thenReturn(true);
        when(userDAO.updatePassword(eq("inesistente@email.com"), anyString())).thenReturn(false);
        when(userDAO.insertUser(any())).thenReturn(false);
        when(userDAO.insertUser(refEq(new UserDTO(
                "nuovo@email.com",
                "Gino",
                "Neri",
                role.libraryUser,
                "password"
        )))).thenReturn(true);
    }

    @Test
    public void login_validCredentials_returnsController() {
        AuthService authService = new ConcreteAuthService(userDAO);
        ControllerInterface libraryUserController = assertDoesNotThrow(() -> authService.login("library.user@email.com", "password"));
        assertInstanceOf(LibraryUserController.class, libraryUserController);
        assertTrue(authService.isLogged());

        ControllerInterface librarianController = assertDoesNotThrow(() -> authService.login("librarian@email.com", "bibliotecario"));
        assertInstanceOf(LibrarianController.class, librarianController);
        assertTrue(authService.isLogged());

        ControllerInterface libraryAdminController = assertDoesNotThrow(() -> authService.login("admin@email.com", "admin"));
        assertInstanceOf(LibraryAdministratorController.class, libraryAdminController);
        assertTrue(authService.isLogged());
    }

    //forse questo non serve perchè sto testando il mock...
    @Test
    public void login_invalidEmail_throwsException() {
        AuthService authService = new ConcreteAuthService(userDAO);
        assertThrows(UserNotFoundException.class, () -> authService.login("inesistente@email.com", "password"));
    }

    @Test
    public void login_invalidPassword_throwsException() {
        AuthService authService = new ConcreteAuthService(userDAO);
        var e = assertThrows(UserNotFoundException.class, () -> authService.login("library.user@email.com", "Password"));
        assertEquals("Wrong username or password", e.getMessage());
    }
    @Test
    public void logout_clearsLoggedUser() {
        AuthService authService = new ConcreteAuthService(userDAO);
        assertDoesNotThrow(() -> authService.login("library.user@email.com", "password"));
        authService.logout();
        assertFalse(authService.isLogged());
    }

    @Test
    public void resetPassword_updatesPassword() {
        AuthService authService = new ConcreteAuthService(userDAO);
        assertDoesNotThrow(() -> authService.resetPassword("library.user@email.com", "nuovaPassword"));
    }

    @Test
    public void resetPassword_nonExistentEmail_throwsException() {
        AuthService authService = new ConcreteAuthService(userDAO);
        var e = assertThrows(RuntimeException.class, () -> authService.resetPassword("inesistente@email.com", "nuovaPassword"));
        assertEquals("Password reset failed", e.getMessage());
    }

    @Test
    public void register_newUser_returnsTrue() {
        AuthService authService = new ConcreteAuthService(userDAO);
        assertTrue(authService.register(new UserDTO(
                "nuovo@email.com",
                "Gino",
                "Neri",
                role.libraryUser,
                "password"
        )));
    }
    @Test
    public void register_existingUser_throws(){
        AuthService authService = new ConcreteAuthService(userDAO);
        var e = assertThrows(RuntimeException.class, () -> authService.register(new UserDTO(
                "library.user@email.com",
                "Mario",
                "Rossi",
                role.libraryUser,
                "password"
        )));
        assertEquals("User already exists", e.getMessage());
    }

    @Test
    public void register_errorInsertingUser_throws() {
        AuthService authService = new ConcreteAuthService(userDAO);
        var e = assertThrows(RuntimeException.class, () -> authService.register(new UserDTO(
                "neri.neri@email.com",
                "Neri",
                "Neri",
                role.libraryUser,
                "password"
        )));
        assertEquals("User registration failed", e.getMessage());
    }
}

