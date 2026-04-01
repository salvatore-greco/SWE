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
    private UserDAO userDAO;

    public ConcreteAuthService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @Override
    public ControllerInterface login(String email, String password) throws UserNotFoundException {
        UserDTO userDTO = userDAO.getUserByEmail(email);
        ControllerFactory factory;

        if (!BCrypt.checkpw(password, userDTO.getPassword())) {
            throw new UserNotFoundException("Wrong username or password");
        }

        switch (userDTO.getRole()) {
            case role.libraryUser:
                loggedUser = new LibraryUser.LibraryUserBuilder()
                        .setName(userDTO.getName())
                        .setSurname(userDTO.getSurname())
                        .setEmail(userDTO.getEmail())
                        .build();
                factory = new LibraryUserControllerFactory();
                break;
            case role.librarian:
                loggedUser = new Librarian(userDTO.getName(), userDTO.getEmail(), userDTO.getSurname());
                factory = new LibrarianControllerFactory();
                break;
            case role.libraryAdministrator:
                loggedUser = new LibraryAdministrator(userDTO.getName(), userDTO.getSurname(), userDTO.getEmail());
                factory = new LibraryAdministratorControllerFactory();
                break;
            default:
                //FIXME: creare l'eccezione corretta e mettere un messaggio decente
                throw new RuntimeException("broski non esiste il ruolo " + userDTO.getRole());
        }
        return factory.createController(loggedUser);
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

    @Override
    public void logout() {
        loggedUser = null;
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        boolean updated = userDAO.updatePassword(email, hashedPassword);
        if (!updated) {
            throw new RuntimeException("Password reset failed");
        }
    }

    @Override
    public boolean register(UserDTO userDTO) {
        if(loggedUser!=null)
            throw new RuntimeException("User already logged in");
        if (userDAO.getUserByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());

        boolean created = userDAO.insertUser(userDTO);
        if (!created) {
            throw new RuntimeException("User registration failed");
        }
        return created;
    }
}
