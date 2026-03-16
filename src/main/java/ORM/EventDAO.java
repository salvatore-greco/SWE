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

    public boolean addNewParticipant(Event event, LibraryUser user) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO partecipation (event, user) values (?, ?)");
            stmt.setInt(1, event.getId());
            stmt.setString(2, user.getEmail());
            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean removeParticipant(Event event, LibraryUser user) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM partecipation WHERE event = ? AND user = ?");
            stmt.setInt(1, event.getId());
            stmt.setString(2, user.getEmail());
            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
                    Integer id = rs.getInt(1);
                    stmt.close();
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteEvent(Event event) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM event WHERE id = ?");
            stmt.setInt(1, event.getId());
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editEvent(Event event) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE event SET name = ?, description = ?, date = ?, duration = ?, organizer = ?, room = ? WHERE id = ?");
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(event.getStartDate()));
            stmt.setString(4, event.getEventDuration().toString());
            stmt.setString(5, event.getOrganizer().getEmail());
            stmt.setInt(6, event.getPlace().getNumber());
            stmt.setInt(7, event.getId());

            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}