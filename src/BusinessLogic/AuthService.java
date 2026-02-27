package BusinessLogic;

public interface AuthService {
    public ControllerInterface login(String user, String password);
    public void logout();
    public void resetPassword();
}
