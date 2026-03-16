package ORM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

public class UserDAOUnitTest {
    private UserDAO userDAO;

    @BeforeEach
    public void init() throws Exception {
        userDAO = new UserDAO();

        Connection conn = ConnectionManager.getInstance().getConnection();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DELETE FROM \"user\"");
    }
}
