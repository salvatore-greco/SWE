package BusinessLogic;

import DomainModel.*;
import ORM.BookDAO;
import ORM.LibraryDAO;
import ORM.UserDAO;

public class LibraryAdministratorController implements ControllerInterface {
    private LibraryAdministrator user;

    public LibraryAdministratorController(User user) {
        this.user = (LibraryAdministrator) user;
        if (this.user.getLibraryManaged() == null) {
            UserDAO userDao = new UserDAO();
            this.user.setLibraryManaged(userDao.getLibraryManagedByAdmin(this.user.getEmail())
            );
        }
    }

    public boolean increaseBudget(int increase) {
        if (increase <0){
            throw new IllegalArgumentException("Increase must be positive");
        }
        LibraryDAO libraryDAO = new LibraryDAO();
        Library library = user.getLibraryManaged();
        library.setBudget(library.getBudget() + increase);
        return libraryDAO.increaseBudgetOfLibrary(library.getName(), library.getBudget());
    }

    public boolean addBookToCatalogue(Book newBook) {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.saveBook(newBook);
    }

    public boolean removeBookFromCatalogue(Book book) {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.deleteBook(book.getCode());
    }

    public boolean updateBookInCatalogue(Book updatedBook) {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.updateBook(updatedBook);
    }

    public LibraryAdministrator getUser() {
        return user;
    }
}
