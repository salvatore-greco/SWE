package ORM;

import DomainModel.Event;
import DomainModel.LibraryUser;
import org.postgresql.util.PGInterval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Integer setEvent(Event event) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO event (name, description, date, duration, organizer, room) values (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(event.getStartDate()));
            //Postgre è compatibile con il to string di duration per gli oggetti di tipo interval
            stmt.setString(4, event.getEventDuration().toString());
            stmt.setString(5, event.getOrganizer().getEmail());
            stmt.setInt(6, event.getPlace().getNumber());

            Integer rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}