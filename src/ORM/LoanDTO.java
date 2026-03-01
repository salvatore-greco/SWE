package ORM;

public class LoanDTO {
    private LocalTime issueDate;
    private LocalTime expirationDate;

    public LoanDTO(LocalTime issueDate, LocalTime expirationDate) {
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public LocalTime getIssueDate() {
        return issueDate;
    }

    public LocalTime getExpirationDate() {
        return expirationDate;
    }
}