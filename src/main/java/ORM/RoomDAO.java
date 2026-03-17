package ORM;

import DomainModel.*;
import BusinessLogic.role;
import org.postgresql.util.PGInterval;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
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
            stmt.close();
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
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<Event> getEventForRoomNumber(int number) {
        try {
            String query = """
                     SELECT e.*, u.*, u.name as user_name\s
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
                        .setOrganizer(new Librarian(rs.getString("user_name"), rs.getString("email"), rs.getString("surname")))
                        .build());
            }
            stmt.close();
            return eventList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("non ho trovato una sega nel db");
    }

    private static Duration PGIntervalToDuration(PGInterval duration) {
        return Duration.ofHours(duration.getHours()).plusMinutes(duration.getMinutes()).plusSeconds(duration.getWholeSeconds());
    }

    private LocalDate toLocalDateOrNull(Date date){
        if (date == null)
            return null;
        else
            return date.toLocalDate();
    }
    private ArrayList<LibraryUser> getReservedSeatForRoomNumber(int number) {
        try {
            String query = """
                    SELECT u.*, c.*\s
                    FROM reservation_study_room as res join "user" as u on res."user"=u.email\s
                    join card as c on c.user = u.email\s
                    where res.room = ? and u.role = ?::role
                   \s""";
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, number);
            stmt.setString(2, role.libraryUser.name());
            ResultSet rs = stmt.executeQuery();
            ArrayList<LibraryUser> userList = new ArrayList<>();
            if (rs.next()) {
                userList.add(new LibraryUser.LibraryUserBuilder()
                        .setName(rs.getString("name"))
                        .setSurname(rs.getString("surname"))
                        .setEmail(rs.getString("email"))
                        .setCard(rs.getInt("id"), toLocalDateOrNull(rs.getDate("issuedate")), toLocalDateOrNull(rs.getDate("expirationdate")))
                        .build()
                );
            }
            stmt.close();
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
                if (rs.getBoolean("is_study_room")) {
                    Room room = new StudyRoom(rs.getInt("number"), rs.getInt("seats"), getReservedSeatForRoomNumber(number));
                    stmt.close();
                    return room;
                }
                else {
                    ArrayList<Event> eventList = getEventForRoomNumber(number);
                    EventRoom eventRoom = new EventRoom(rs.getInt("number"), rs.getInt("seats"));
                    for (Event event : eventList) {
                        event.setPlace(eventRoom);
                    }
                    eventRoom.setScheduledEvents(eventList);
                    stmt.close();
                    return eventRoom;
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}