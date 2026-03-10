package BusinessLogic;

import DomainModel.User;

public interface ControllerFactory {
    public ControllerInterface createController(User user);
}
