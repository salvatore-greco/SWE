package DomainModel;

import java.time.DateTimeException;
import java.time.LocalTime;

public class Card {
    private int id;
    private LocalTime issueDate;
    private LocalTime expirationDate;

    //TODO: verify if every getter and setter is needed

    public int getId() {
        return id;
    }

    public LocalTime getIssueDate() {
        return issueDate;
    }

    public LocalTime getExpirationDate() {
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
    public Card(int id, LocalTime issueDate, LocalTime expirationDate) throws DateTimeException {
        this.id = id;
        if(issueDate.isAfter(expirationDate)){
            throw new DateTimeException("issueDate cannot be after expiration date");
        }
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }
}
