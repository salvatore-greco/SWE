package ORM;

import DomainModel.Book;
import Exception.data.*;

import java.util.ArrayList;

import java.sql.*;

public class BookDAO {

    public BookDAO() {
    }

    public Book getBookByCode(String code) throws BookNotFoundException {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE code = ?");
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book.BookBuilder()
                        .setCode(rs.getString(1))
                        .setISBN(rs.getString(2))
                        .setTitle(rs.getString(3))
                        .build();
            } else
                throw new BookNotFoundException("Book with code " + code + " not found");
        } catch (SQLException e) {
            throw new RuntimeException("Database error while retrieving book", e);
            //TODO: valutare se trasformarla in un eccezione deicata del DAO layer (metterla nel package Exception.data)
        }
    }

    public boolean saveBook(Book book) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (code, isbn, title) VALUES (?, ?, ?)");

            stmt.setString(1, book.getCode());
            stmt.setString(2, book.getISBN());
            stmt.setString(3, book.getTitle());
            int row = stmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();

        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book.BookBuilder()
                        .setCode(rs.getString(1))
                        .setISBN(rs.getString(2))
                        .setTitle(rs.getString(3)).build()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    //TODO: valutare se aggiungere altri metodi in generale (tipo update o delete anche se potrebbero non essere necessari)

    //FIXME: è necessario rimuoverlo dal db oppure basta settare un flag in tabella?
    public boolean deleteBook(String code) {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM book WHERE code = ?");
            stmt.setString(1, code);
            int row = stmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}