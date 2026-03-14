package DomainModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanUnitTest {
    private static Card card;
    private static Book book;

    @BeforeAll
    public static void init() {
        card = new Card(1, LocalDate.now(), LocalDate.now().plusYears(3));
        book = new Book.BookBuilder().setISBN("9781234567897").setTitle("titolo").setAuthors(null).setCode("a001").build();
    }

    @Test
    public void Loan_constructor_requireCardNotNull() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> new Loan(null, book, false, false));
        assertEquals("Card should not be null", e.getMessage());
    }

    @Test
    public void Loan_constructor_requireBookNotNull() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> new Loan(card, null, false, false));
        assertEquals("Book should not be null", e.getMessage());
    }

    @Test
    public void Loan_constructor_grantedEndedCorrectness() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Loan(card, book, false, true));
        assertEquals("Loan cannot be ended but not granted", e.getMessage());
        assertDoesNotThrow(() -> new Loan(card, book, true, true)); //prestito erogato e terminato
        assertDoesNotThrow(() -> new Loan(card, book, false, false)); //prestito richiesto
        assertDoesNotThrow(() -> new Loan(card, book, true, false)); //prestito erogato ma non terminato
    }

    @Test
    public void Loan_constructor_illegalDateThrows(){
        DateTimeException e = assertThrows(DateTimeException.class, () -> new Loan(card, book, true, false, LocalDate.now(), LocalDate.now().minusDays(1)));
        assertEquals("issueDate cannot be after expiration date", e.getMessage());
    }
}
