package BusinessLogic;

import DomainModel.LibraryUser;
import DomainModel.User;

public class LibraryUserController implements ControllerInterface{
    public LibraryUser user;
    public LibraryUserController(User user){
        this.user = (LibraryUser) user; //FIXME: this smell
    }


}
