package BusinessLogic;

import Exception.data.UserNotFoundException;

public interface AuthService {
    public ControllerInterface login(String email, String password) throws UserNotFoundException;
    public void logout();
    public void resetPassword(String email, String newPassword);
    public boolean register(String name, String surname, String email, String password);
}
