package DomainModel;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private Card card;
    private Book book;
    private LocalDate issueDate = null;
    private LocalDate expirationDate = null;
    private boolean granted; // se vero l'utente ha il libro (fisicamente), falso ha fatto solo richiesta
    private boolean ended; // se vero l'utente ha reso il libro alla biblioteca

    public Loan(Card card, Book book, boolean granted, boolean ended) {
        Objects.requireNonNull(card, "Card should not be null");
        Objects.requireNonNull(book, "Book should not be null");
        if(granted == false && ended == true)
            throw new IllegalArgumentException("Loan cannot be ended but not granted");
        this.card = card;
        this.book = book;
        this.granted = granted;
        this.ended = ended;
    }

    /**
     *
     * @param card
     * @param book
     * @param issueDate
     * @param expirationDate
     * @throws DateTimeException when issueDate is after expirationDate
     */
    public Loan(Card card, Book book, boolean granted, boolean ended, LocalDate issueDate, LocalDate expirationDate) {
        this(card,book,granted,ended);
        if(issueDate.isAfter(expirationDate)){
            throw new DateTimeException("issueDate cannot be after expiration date");
        }
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean getGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public boolean getEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
