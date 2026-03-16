package ORM;

import BusinessLogic.role;
import DomainModel.Library;
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
                UserDTO userDTO =  new UserDTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        role.valueOf(rs.getString(4)),
                        rs.getString(5)
                );
                stmt.close();
                return userDTO;
            }
            else
                throw new UserNotFoundException("Wrong username or password");
                //dato che lanciamo la stessa eccezione anche nel concrete auth service
                //per non dare informazioni sul fatto che l'email sia presente o meno nel db
                //usiamo lo stesso messaggio dell'eccezione
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    public Library getLibraryManagedByAdmin(String email){
       try{
           Connection conn = ConnectionManager.getInstance().getConnection();
           PreparedStatement stmt = conn.prepareStatement("SELECT name, budget from manage JOIN library on library.name = manage.library where manage.user = (?)");
           stmt.setString(1, email);
           ResultSet rs = stmt.executeQuery();
              if(rs.next()){
                    Library library = new Library(rs.getInt(2), rs.getString(2));
                    stmt.close();
                    return library;
              }
       } catch (SQLException e) {
           e.printStackTrace();
       }
        return null;
    }

    public boolean updatePassword(String email, String newHashedPassword){
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE user SET hashedPassword = ? WHERE email = ?");
            stmt.setString(1, newHashedPassword);
            stmt.setString(2, email);
            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertUser(UserDTO userDTO){
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            //forzo il cast del ruolo nella enum postgre
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (email, name, surname, hashedPassword, role) values (?, ?, ?, ?, ?::role)");
            stmt.setString(1, userDTO.getEmail());
            stmt.setString(2, userDTO.getName());
            stmt.setString(3, userDTO.getSurname());
            stmt.setString(4, userDTO.getHashedPassword());
            stmt.setString(5, userDTO.getRole().name());

            int row = stmt.executeUpdate();
            stmt.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
