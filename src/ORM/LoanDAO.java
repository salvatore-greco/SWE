package ORM;

import DomainModel.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
