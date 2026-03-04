package ORM;

import DomainModel.*;

import java.sql.*;

public class CardDAO {

    public CardDAO() {
    }

    public Integer setRequestedCard(LibraryUser applicantUser){
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO card (user) values (?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, applicantUser.getEmail());
            int row = stmt.executeUpdate();
            if(row > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}