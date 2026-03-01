package ORM;

import java.time.LocalDate;

public class CardDTO {
    private int id;
    private LocalDate issueDate;
    private LocalDate expirationDate;

    public CardDTO(int id, LocalDate issueDate, LocalDate expiryDate) {
        this.id = id;
        this.issueDate = issueDate;
        this.expirationDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpiryDate() {
        return expirationDate;
    }
}