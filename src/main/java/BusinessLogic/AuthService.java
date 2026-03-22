package BusinessLogic;

import Exception.data.UserNotFoundException;
import ORM.UserDTO;

public interface AuthService {
    public ControllerInterface login(String email, String password) throws UserNotFoundException;
    public void logout();
    public void resetPassword(String email, String newPassword);
    public boolean register(UserDTO userDTO);
    public boolean isLogged();
}
