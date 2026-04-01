package IntegrationTest.ORM;

import DomainModel.Book;
import Exception.data.BookNotFoundException;
import org.junit.jupiter.api.Test;
import ORM.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookDAOIntegrationTest extends BaseIntegrationTest {

    @Test
    public void BookDAO_getBookByCode_returnsObject() {
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.getBookByCode("A001");
        assertEquals("A001", book.getCode());
        assertEquals("1234567890123", book.getISBN());
        assertEquals("titolo di un libro", book.getTitle());
    }

    @Test
    public void BookDAO_getBookByCode_throwsExceptionIfNotExist() {
        BookDAO bookDAO = new BookDAO();
        assertThrows(BookNotFoundException.class, () -> bookDAO.getBookByCode("B002"));
    }

    @Test
    public void BookDAO_saveBook_returnsTrue() {
        BookDAO bookDAO = new BookDAO();
        Book book = new Book.BookBuilder()
                .setCode("A003")
                .setISBN("1234567890125")
                .setTitle("un altro libro ancora")
                .build();
        assertTrue(bookDAO.saveBook(book));
        Book retrievedBook = bookDAO.getBookByCode("A003");
        assertEquals("A003", retrievedBook.getCode());
        assertEquals("1234567890125", retrievedBook.getISBN());
        assertEquals("un altro libro ancora", retrievedBook.getTitle());
    }

    @Test
    public void BookDAO_getAllBooks() {
        BookDAO bookDAO = new BookDAO();
        assertEquals(2, bookDAO.getAllBooks().size());
        assertEquals("A001", bookDAO.getAllBooks().getFirst().getCode());
        assertEquals("A002", bookDAO.getAllBooks().get(1).getCode());
    }

    @Test
    public void BookDAO_deleteBook_returnsTrue() {
        BookDAO bookDAO = new BookDAO();
        assertTrue(bookDAO.deleteBook("A002"));
        assertThrows(BookNotFoundException.class, () -> bookDAO.getBookByCode("A002"));
    }

    @Test
    public void BookDAO_deleteBook_falseIfNotExist() {
        BookDAO bookDAO = new BookDAO();
        assertFalse(bookDAO.deleteBook("B002"));
    }

    @Test
    public void BookDAO_updateBook_returnsTrue() {
        BookDAO bookDAO = new BookDAO();
        Book updatedBook = new Book.BookBuilder()
                .setCode("A001")
                .setISBN("1234567890124")
                .setTitle("titolo di un libro aggiornato")
                .build();
        assertTrue(bookDAO.updateBook(updatedBook));
        Book retrievedBook = bookDAO.getBookByCode("A001");
        assertEquals("A001", retrievedBook.getCode());
        assertEquals("1234567890124", retrievedBook.getISBN());
        assertEquals("titolo di un libro aggiornato", retrievedBook.getTitle());
    }
    @Test
    public void BookDAO_updateBook_falseIfNotExist() {
        BookDAO bookDAO = new BookDAO();
        Book updatedBook = new Book.BookBuilder()
                .setCode("B002")
                .setISBN("1234567890124")
                .setTitle("titolo di un libro aggiornato")
                .build();
        assertFalse(bookDAO.updateBook(updatedBook));
    }
}
