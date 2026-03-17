package ORM;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionManagerUnitTest {
    @Test
    public void ConnectionManager_testConnection(){
        Connection connection;
        connection = assertDoesNotThrow(
                () -> ConnectionManager.getInstance().getConnection()
        );

        try {
            assertTrue(connection.isValid(1));
        } catch (SQLException e) {
            fail("Connection is not vaild, it throws an SQLException");
            e.printStackTrace();
        }
        try {
            connection.close();
            // ConnectionManager.connection è final. Non puoi riassegnarlo a meno che non reistanzi un altro oggetto
            // Non so quanto senso abbia sto test dato che nei dao non viene mai chiamato connection close
            // potrebbe avere senso se 2 dao agissero in parallelo. Fallo tornare ma non ammattire.
            connection = assertDoesNotThrow(
                    () -> ConnectionManager.getInstance().getConnection()
            );
            assertTrue(connection.isValid(1));
        } catch (SQLException e) {
            fail("Connection is not vaild, it throws an SQLException");
            e.printStackTrace();
        }
    }
}
