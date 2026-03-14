package ORM;

import DomainModel.*;
import BusinessLogic.role;
import org.postgresql.util.PGInterval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;

public class RoomDAO {

    public RoomDAO() {
    }

    public boolean addSeatReservationStudyRoom(StudyRoom room, LibraryUser user) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO reservation_study_room (\"user\", room) VALUES (?, ?)");
            stmt.setString(1, user.getEmail());
            stmt.setInt(2, room.getNumber());
            int row = stmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSeatReservationStudyRoom(StudyRoom room, LibraryUser user) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM reservation_study_room WHERE \"user\" = ? AND room = ?");
            stmt.setString(1, user.getEmail());
            stmt.setInt(2, room.getNumber());
            int row = stmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<Event> getEventForRoomNumber(int number) {
        try {
            String query = """
                     SELECT e.*, u.*\s
                     FROM room as r JOIN event as e on r.number = e.room\s
                     JOIN "user" as u on u.email = e.organizer\s
                     WHERE r.number = ?
                    \s""";
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, number);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Event> eventList = new ArrayList<>();
            if (rs.next()) {
                PGInterval pgInterval = (PGInterval) rs.getObject("duration");
                eventList.add(new Event.EventBuilder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setDescription(rs.getString("description"))
                        .setStartDate(rs.getTimestamp("date").toLocalDateTime())
                        .setEventDuration(PGIntervalToDuration(pgInterval))
                        .setOrganizer(new Librarian(rs.getString("u.name"), rs.getString("u.email"), rs.getString("u.surname")))
                        .build());
            }
            return eventList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("non ho trovato una sega nel db");
    }

    private static Duration PGIntervalToDuration(PGInterval duration) {
        return Duration.ofHours(duration.getHours()).plusMinutes(duration.getMinutes()).plusSeconds(duration.getWholeSeconds());
    }

    private ArrayList<LibraryUser> getReservedSeatForRoomNumber(int number) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT u.* FROM reservation_study_room as res join \"user\" as u on res.\"user\"=u.email join card as c on c.user = u.email where res.room = ? and u.role = ?");
            stmt.setInt(1, number);
            stmt.setString(2, role.libraryUser.name());
            ResultSet rs = stmt.executeQuery();
            ArrayList<LibraryUser> userList = new ArrayList<>();
            if (rs.next()) {
                userList.add(new LibraryUser.LibraryUserBuilder()
                        .setName(rs.getString("u.name"))
                        .setSurname(rs.getString("u.surname"))
                        .setEmail("u.email")
                        .setCard(rs.getInt("c.id"), rs.getDate("c.issuedate").toLocalDate(), rs.getDate("c.expirationdate").toLocalDate())
                        .build()
                );
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("manco qua ho trovato nulla nel db");
    }

    public Room getRoomByNumber(int number) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM room WHERE number = ?");
            stmt.setInt(1, number);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getBoolean("is_study_room"))
                    return new StudyRoom(rs.getInt("number"), rs.getInt("seats"), getReservedSeatForRoomNumber(number));
                else {
                    ArrayList<Event> eventList = getEventForRoomNumber(number);
                    EventRoom eventRoom = new EventRoom(rs.getInt("number"), rs.getInt("seats"));
                    for (Event event : eventList) {
                        event.setPlace(eventRoom);
                    }
                    return eventRoom;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}