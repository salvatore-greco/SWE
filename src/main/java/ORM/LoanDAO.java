package ORM;

import DomainModel.Book;
import DomainModel.Card;
import DomainModel.Loan;

import java.sql.*;
import java.time.LocalDate;

public class LoanDAO {
    public LoanDAO() {
    }

    public boolean setLoan(Loan loan) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO loan(book, card, granted, ended) values(?, ?, ?, ?) ");
            stmt.setString(1, loan.getBook().getCode());
            stmt.setInt(2, loan.getCard().getId());
            stmt.setBoolean(3, loan.getGranted());
            stmt.setBoolean(4, loan.getEnded());
            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBookLoaned(String code) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM loan WHERE book = ? AND ended = FALSE AND granted = TRUE");
            stmt.setString(1, code);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean grantLoan(Loan loan, LocalDate issueDate, LocalDate expirationDate) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE loan SET granted = TRUE, issueDate = ?, expirationDate = ? WHERE book = ? AND card = ? AND ended = FALSE");
            stmt.setDate(1, Date.valueOf(issueDate));
            stmt.setDate(2, Date.valueOf(expirationDate));
            stmt.setString(3, loan.getBook().getCode());
            stmt.setInt(4, loan.getCard().getId());
            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean endLoan(Loan loan) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE loan SET ended = ? WHERE book = ? AND card = ?");
            stmt.setBoolean(1, true);
            stmt.setString(2, loan.getBook().getCode());
            stmt.setInt(3, loan.getCard().getId());
            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Loan getLoanByBookAndCard(String code, int card) {
        try {
            String query = """
                    SELECT *\s
                    FROM loan AS l JOIN card AS c ON l.card = c.id JOIN book on book.code = l.book\s
                    WHERE book = ? AND card = ?
                   \s""";
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, code);
            stmt.setInt(2, card);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int cardId = rs.getInt("card");
                String user = rs.getString("user");
                LocalDate cardIssueDate = Utils.toLocalDateOrNull(rs.getDate("issuedate"));
                LocalDate cardExpirationDate = Utils.toLocalDateOrNull(rs.getDate("expirationdate"));
                boolean granted = rs.getBoolean("granted");
                boolean ended = rs.getBoolean("ended");
                String bookCode = rs.getString("book");
                String title = rs.getString("title");
                String isbn = rs.getString("isbn");
                stmt.close();
                return new Loan(new Card(cardId, cardIssueDate, cardExpirationDate),
                        new Book.BookBuilder().setCode(bookCode).setISBN(isbn).setTitle(title).build(),
                        granted, ended, cardIssueDate, cardExpirationDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
