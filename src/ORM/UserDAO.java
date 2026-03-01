package ORM;

import DomainModel.LibraryUser;
import DomainModel.User;
import Exception.data.UserNotFoundException;

import java.sql.*;

public class UserDAO {

    public UserDAO() {
    }

    /**
     *
     * @param email
     * @return {@link UserDTO} if user with given email is found. Can return null if a database error happens
     * @throws UserNotFoundException
     */
    public UserDTO getUserByEmail(String email) throws UserNotFoundException {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            }
            else
                throw new UserNotFoundException("Wrong username or password");
                //dato che lanciamo la stessa eccezione anche nel concrete auth service
                //per non dare informazioni sul fatto che l'email sia presente o meno nel db
                //usiamo lo stesso messaggio dell'eccezione
        }
        catch (SQLException e){
            e.printStackTrace();
            return null; //FIXME: smell. Refactor
        }

    }
}
