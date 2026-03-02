package ORM;

import DomainModel.Event;
import DomainModel.LibraryUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventDAO {

    public EventDAO() {
    }

    public void addNewParticipant(Event event, LibraryUser user) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO partecipation (event, user) values (?, ?)");
            stmt.setInt(1, event.getId());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeParticipant(Event event, LibraryUser user) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM partecipation WHERE event = ? AND user = ?");
            stmt.setInt(1, event.getId());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}