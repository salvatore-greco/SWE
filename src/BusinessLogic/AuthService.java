package BusinessLogic;

import Exception.data.UserNotFoundException;

public interface AuthService {
    public ControllerInterface login(String email, String password) throws UserNotFoundException;
    public void logout();
    public void resetPassword();
    public boolean register(String email, String password);
}
