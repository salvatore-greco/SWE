package ORM;

import DomainModel.Book;

import java.sql.*;

public class BookDAO {

    public BookDAO() {
    }

    public BookDTO getBookByCode(String code) throws BookNotFoundException {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE code = ?");
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BookDTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
            }
            else
                throw new BookNotFoundException("Book with code " + code + " not found");
        }
        catch (SQLException e){
            e.printStackTrace();
            return null; //FIXME same as in UserDAO
        }

    }
}