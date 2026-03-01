package ORM;

import java.util.LocalDate;

public class CardDTO {
    private int id;
    private LocalDate issueDate;
    private LocalDate expirationDate;

    public CardDTO(int id, LocalDate issueDate, LocalDate expiryDate) {
        this.id = id;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}