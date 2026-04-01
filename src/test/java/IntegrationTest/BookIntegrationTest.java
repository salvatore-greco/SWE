package IntegrationTest;

import BusinessLogic.*;
import DomainModel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BookIntegrationTest extends BaseIntegrationTest {
    private static LibraryAdministratorController adminController;

    @BeforeAll
    public static void init(){
        adminController = new LibraryAdministratorController(
                new LibraryAdministrator("Admin", "admin@email.com", "Admin")
        );
    }

    private Book createBook(){
        Book book = new Book.BookBuilder()
                .setISBN("1234567890")
                .setTitle("Test Book")
                .setAuthors(new ArrayList<String>(){{
                    add("Author One");
                    add("Author Two");
                }})
                .setCode("TEST001")
                .build();
        return book;
    }

    @Test
    public void addBookToCatalogue(){
        Book bookToAdd = createBook();
        assertDoesNotThrow(() -> adminController.addBookToCatalogue(bookToAdd));
    }

    @Test
    public void addBookToCatalogue_duplicateBook_throws(){
        Book bookToAdd = createBook();
        adminController.addBookToCatalogue(bookToAdd);
        Book bookDuplicated = createBook();
        boolean result = adminController.addBookToCatalogue(bookDuplicated);
        assertFalse(result);
    }

    @Test
    public void removeBookFromCatalogue(){
        Book bookToRemove = createBook();
        adminController.addBookToCatalogue(bookToRemove);
        assertDoesNotThrow(() -> adminController.removeBookFromCatalogue(bookToRemove));
    }

    @Test
    public void removeBookFromCatalogue_bookNotAdded_throws(){
        Book bookToRemove = createBook();
        boolean result = adminController.removeBookFromCatalogue(bookToRemove);
        assertFalse(result);
    }

     @Test
    public void updateBookInCatalogue() {
         Book bookToUpdate = createBook();
         adminController.addBookToCatalogue(bookToUpdate);

         Book updatedBook = new Book.BookBuilder()
                 .setCode(bookToUpdate.getCode())
                 .setISBN(bookToUpdate.getISBN())
                 .setTitle("Updated Test Book")
                 .setAuthors(bookToUpdate.getAuthors())
                 .build();

         boolean result = adminController.updateBookInCatalogue(updatedBook);
         assertTrue(result);
    }

    @Test
    public void updateBookInCatalogue_notExistingBook(){
        Book bookToUpdate = createBook();
        boolean result = adminController.updateBookInCatalogue(bookToUpdate);
        assertFalse(result);
    }
}
