package ORM;

import DomainModel.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryDAO {
    public LibraryDAO() {
    }

    public Library getLibraryByName(String name) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM library WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Library library = new Library(rs.getInt("budget"), rs.getString("name"));
                stmt.close();
                return library;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean increaseBudgetOfLibrary(String name, int budgetIncreased){
        try{
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE library set budget = (?) where name = (?)");
            stmt.setInt(1, budgetIncreased);
            stmt.setString(2, name);
            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
