package DomainModel;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Objects;

public class Loan {
    private Card card;
    private Book book;
    private LocalTime issueDate = null;
    private LocalTime expirationDate = null;
    private boolean granted; // se vero l'utente ha il libro (fisicamente), falso ha fatto solo richiesta
    private boolean ended; // se vero l'utente ha reso il libro alla biblioteca

    public Loan(Card card, Book book, boolean granted, boolean ended) {
        Objects.requireNonNull(card, "Card should not be null");
        Objects.requireNonNull(book, "Book should not be null");
        this.granted = granted;
        if(ended == true && this.granted == false)
            throw new IllegalArgumentException("Load cannot be ended but not granted");
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
    public Loan(Card card, Book book, LocalTime issueDate, LocalTime expirationDate, boolean granted, boolean ended) {
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

    public LocalTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalTime expirationDate) {
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
