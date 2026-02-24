package DomainModel;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Objects;

public class Loan {
    private Card card;
    private Book book;
    private LocalTime issueDate;
    private LocalTime expirationDate;

    /**
     *
     * @param card
     * @param book
     * @param issueDate
     * @param expirationDate
     * @throws DateTimeException when issueDate is after expirationDate
     */
    public Loan(Card card, Book book, LocalTime issueDate, LocalTime expirationDate) {
        Objects.requireNonNull(card, "Card should not be null");
        Objects.requireNonNull(book, "Book should not be null");
        if(issueDate.isAfter(expirationDate)){
            throw new DateTimeException("issueDate cannot be after expiration date");
        }
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }
}
