package ORM;

import DomainModel.LibraryUser;
import DomainModel.Room;
import DomainModel.StudyRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDAO {

    public RoomDAO() {
    }

    public boolean addSeatReservationStudyRoom(StudyRoom room, LibraryUser user){
        try{
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO reservation_study_room (\"user\", room) VALUES (?, ?)");
            stmt.setString(1, user.getEmail());
            stmt.setInt(2, room.getNumber());
            int row = stmt.executeUpdate();
            return row > 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSeatReservationStudyRoom(StudyRoom room, LibraryUser user){
        try{
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM reservation_study_room WHERE \"user\" = ? AND room = ?");
            stmt.setString(1, user.getEmail());
            stmt.setInt(2, room.getNumber());
            int row = stmt.executeUpdate();
            return row > 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //TODO: può essere utile ai fini della relazione fare un metodo per prendere il numero di prenotazioni di una stanza
    // da usare in un ipotetica vista
}