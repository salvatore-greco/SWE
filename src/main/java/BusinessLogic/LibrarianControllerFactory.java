package BusinessLogic;

import DomainModel.User;

public class LibrarianControllerFactory implements ControllerFactory{
    @Override
    public ControllerInterface createController(User user) {
        return new LibrarianController(user);
    }
}
