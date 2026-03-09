package BusinessLogic;

import DomainModel.Librarian;
import DomainModel.LibraryAdministrator;
import DomainModel.LibraryUser;
import DomainModel.User;
import Exception.data.UserNotFoundException;
import ORM.UserDAO;
import ORM.UserDTO;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class ConcreteAuthService implements AuthService {
    private User loggedUser;

    @Override
    public ControllerInterface login(String email, String password) throws UserNotFoundException {
        UserDAO userDAO = new UserDAO();
        UserDTO userDTO = userDAO.getUserByEmail(email);
        ControllerFactory factory;
        User user;

        if (!BCrypt.checkpw(password, userDTO.getHashedPassword())) {
            throw new UserNotFoundException("Wrong username or password");
        }

        switch (userDTO.getRole()) {
            default:
                //FIXME: creare l'eccezione corretta e mettere un messaggio decente
                throw new RuntimeException("broski non esiste il ruolo " + userDTO.getRole());
            case "libraryUser":
                user = new LibraryUser.LibraryUserBuilder()
                        .setName(userDTO.getName())
                        .setSurname(userDTO.getSurname())
                        .setEmail(userDTO.getEmail())
                        .build();
                loggedUser = user;
                factory = new LibraryUserControllerFactory();
                return factory.createController(user);
            case "librarian":
                user = new Librarian(userDTO.getName(), userDTO.getEmail(), userDTO.getSurname());
                loggedUser = user;
                factory = new LibrarianControllerFactory();
                return factory.createController(user);
            case "libraryAdministrator":
                user = new LibraryAdministrator(userDTO.getName(), userDTO.getSurname(), userDTO.getEmail());
                loggedUser = user;
                factory = new LibraryAdministratorControllerFactory();
                return factory.createController(user);
        }
    }//TODO: adjust the method to be more readable

    public boolean isLogged() {
        return loggedUser != null;
    }

    @Override
    public void logout() {
        loggedUser = null;
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        UserDAO userDAO = new UserDAO();
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        boolean updated = userDAO.updatePassword(email, hashedPassword);
        if (!updated) {
            throw new RuntimeException("Password reset failed");
        }
    }

    @Override
    public boolean register(String name, String surname, String email, String password) {
        UserDAO userDAO = new UserDAO();
        if (userDAO.getUserByEmail(email) != null) {
            throw new RuntimeException("User already exists");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        UserDTO userDTO = new UserDTO(email, name, surname,"libraryUser", hashedPassword);

        boolean created = userDAO.insertUser(userDTO);
        if (!created) {
            throw new RuntimeException("User registration failed");
        }
        return created;
    }
}
