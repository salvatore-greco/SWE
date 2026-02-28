package BusinessLogic;

import DomainModel.Librarian;
import DomainModel.User;

public class LibrarianController implements ControllerInterface{
    private Librarian user;
    public LibrarianController(User user){
        this.user = (Librarian) user;
    }
}
