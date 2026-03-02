package ORM;

import DomainModel.*;

import java.sql.*;

public class CardDAO {

    public CardDAO() {
    }

    public int setRequestedCard(LibraryUser applicantUser){
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO card (user) values (?)");
            stmt.setString(1, applicantUser.getEmail());
            int row = stmt.executeUpdate();
            if(row > 0){
                PreparedStatement statement = conn.prepareStatement("SELECT id from card where user = (?)");
                statement.setString(1, applicantUser.getEmail());
                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Write on database failed"); //fixme: use an appropriate exception
    }
}