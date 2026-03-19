package ORM;

import BusinessLogic.role;
import DomainModel.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanDAOUnitTest extends BaseDAOUnitTest{
    private LoanDAO loanDAO = new LoanDAO();

    private BookDAO bookDAO = new BookDAO();
    private UserDAO userDAO = new UserDAO();
    private CardDAO cardDAO = new CardDAO();

    private Book createBookTest(){
        Book book = new Book.BookBuilder()
                .setCode("A003")
                .setISBN("1234567890125")
                .setTitle("un altro libro ancora")
                .build();
        bookDAO.saveBook(book);
        return book;
    }

    private Card createCardTest(){
        UserDTO user = new UserDTO(
                "prova3@email.com",
                "UtenteTest",
                "cognomeTest",
                role.valueOf("libraryUser"),
                "hashedPassword"
        );
        userDAO.insertUser(user);

        LibraryUser libraryUser = new LibraryUser.LibraryUserBuilder()
                .setName("UtenteTest")
                .setSurname("cognomeTest")
                .setEmail("prova3@email.com")
                .build();

        Card card = cardDAO.setRequestedCard(libraryUser);
        assertNotNull(card);
        return card;
    }

    private Loan createLoanTest(Book book, Card card){
        return new Loan(card, book, false, false);
    }

    @Test
    public void LoanDAO_setRequestedLoan_returnsTrue() {
        Book book = createBookTest();
        Card card = createCardTest();
        Loan loan = createLoanTest(book, card);

        assertTrue(loanDAO.setRequestedLoan(loan));
    }

     @Test
     public void LoanDAO_isBookLoaned_returnsTrue() {
         assertTrue(loanDAO.isBookLoaned("A001"));
     }

     @Test
     public void LoanDAO_isBookLoaned_bookNotOnLoan_returnsFalse() {
         assertFalse(loanDAO.isBookLoaned("A002"));
     }

      @Test
      public void LoanDAO_isBookLoaned_bookNotExist_returnsFalse(){
          assertFalse(loanDAO.isBookLoaned("B003"));
      }

     @Test
    public void LoanDAO_grantLoan_returnsTrue(){
        Book book = createBookTest();
        Card card = createCardTest();
        Loan loan = createLoanTest(book, card);

        loanDAO.setRequestedLoan(loan);

        boolean result = loanDAO.grantLoan(loan, LocalDate.now(), LocalDate.now().plusDays(30));

        assertTrue(result);
     }

     @Test
    public void LoanDAO_grantLoan_loanNotGranted_returnsFalse() {
        Book book = createBookTest();
        Card card = createCardTest();
        Loan loan = createLoanTest(book, card);

        boolean result = loanDAO.grantLoan(loan, LocalDate.now(), LocalDate.now().plusDays(30));

        assertFalse(result);
     }

     @Test
    public void LoanDAO_endLoan_existingLoan_returnsTrue() {
        Book book = createBookTest();
        Card card = createCardTest();
        Loan loan = createLoanTest(book, card);

        loanDAO.setRequestedLoan(loan);
        loanDAO.grantLoan(loan, LocalDate.now(), LocalDate.now().plusDays(30));

        boolean result = loanDAO.endLoan(loan);

        assertTrue(result);
     }

     @Test
     public void LoanDAO_endLoan_notExistingLoan_returnsFalse(){
            Book book = createBookTest();
            Card card = createCardTest();
            Loan loan = createLoanTest(book, card);

            boolean result = loanDAO.endLoan(loan);

            assertFalse(result);
     }
}
