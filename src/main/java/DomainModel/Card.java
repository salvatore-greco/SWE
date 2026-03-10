package DomainModel;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Card {
    private int id;
    private LocalDate issueDate;
    private LocalDate expirationDate;


    public int getId() {
        return id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }


    public Card(int id){
        this.id = id;
        issueDate = null;
        expirationDate = null;
    }

    /**
     *
     * @param id
     * @param issueDate
     * @param expirationDate
     * @throws DateTimeException when issueDate is after expirationDate
     */
    public Card(int id, LocalDate issueDate, LocalDate expirationDate) throws DateTimeException {
        this.id = id;
        if(issueDate.isAfter(expirationDate)){
            throw new DateTimeException("issueDate cannot be after expiration date");
        }
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }
}
