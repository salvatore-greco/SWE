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
            throw new RuntimeException("Database error while retrieving book", e);
            //TODO: valutare se trasformarla in un eccezione deicata del DAO layer (metterla nel package Exception.data)
        }
    }

    public void saveBook(BookDTO book) throws SQLException {
        Connection conn = ConnectionManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (code, isbn, title) VALUES (?, ?, ?)");

        stmt.setString(1, book.getCode());
        stmt.setString(2, book.getIsbn());
        stmt.setString(3, book.getTitle());
        stmt.executeUpdate();
    }

    public List<BookDTO> getAllBooks()  {
        List<BookDTO> books = new ArrayList<>();

        try{
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new BookDTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    //TODO: valutare se aggiungere altri metodi in generale (tipo update o delete anche se potrebbero non essere necessari)
}