package BusinessLogic;

import DomainModel.User;

public class LibraryAdministratorControllerFactory implements ControllerFactory{
    @Override
    public ControllerInterface createController(User user) {
        return new LibraryAdministratorController(user);
    }
}
