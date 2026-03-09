package ORM;

import DomainModel.*;

import java.sql.*;
import java.time.LocalDate;

public class CardDAO {

    public CardDAO() {
    }

    public Card setRequestedCard(LibraryUser applicantUser) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO card (\"user\") values (?)" , Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, applicantUser.getEmail());
            int row = stmt.executeUpdate();
            if (row > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return new Card(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card createCard(LibraryUser applicantUser) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO card (\"user\", issuedate) values (?, ?)" , Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, applicantUser.getEmail());
            stmt.setDate(2, Date.valueOf(LocalDate.now())); //Approvo la creazione della tessera e imposto la data di scadenza a oggi
            int row = stmt.executeUpdate();
            if (row > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    LocalDate expirationDate = rs.getDate(2).toLocalDate();
                    return new Card(id, LocalDate.now(), expirationDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card createCardFromRequest(LibraryUser applicantUser){
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE card set issuedate = ? where \"user\" = ?" , Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, Date.valueOf(LocalDate.now())); //Approvo la creazione della tessera e imposto la data di scadenza a oggi
            stmt.setString(2, applicantUser.getEmail());
            int row = stmt.executeUpdate();
            if (row > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    LocalDate expirationDate = rs.getDate(2).toLocalDate();
                    return new Card(id, LocalDate.now(), expirationDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}