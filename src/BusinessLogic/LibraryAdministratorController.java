package BusinessLogic;

import DomainModel.LibraryAdministrator;
import DomainModel.User;

public class LibraryAdministratorController implements ControllerInterface{
    private LibraryAdministrator user;

    public LibraryAdministratorController(User user) {
        this.user = (LibraryAdministrator) user;
    }
}
