package ORM;

import DomainModel.Loan;

import java.sql.*;
import java.time.LocalDate;

public class LoanDAO {
    public LoanDAO(){}

    public boolean setRequestedLoan(Loan loan){
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO loan(book, card, granted, ended) values(?, ?, ?, ?) ");
            stmt.setString(1, loan.getBook().getCode());
            stmt.setInt(2, loan.getCard().getId());
            stmt.setBoolean(3, loan.getGranted());
            stmt.setBoolean(4, loan.getEnded());
            int row = stmt.executeUpdate();
            return row > 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBookLoaned(String code) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM loan WHERE book = ? AND ended = FALSE");
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
            stmt.setInt(3, loan.getCard().getId());
            stmt.setString(4, loan.getBook().getCode());
            int row = stmt.executeUpdate();
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
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
