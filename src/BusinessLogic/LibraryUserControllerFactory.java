package BusinessLogic;
import DomainModel.User;

public class LibraryUserControllerFactory implements ControllerFactory{
    @Override
    public ControllerInterface createController(User user) {
        return new LibraryUserController(user);
    }
}
