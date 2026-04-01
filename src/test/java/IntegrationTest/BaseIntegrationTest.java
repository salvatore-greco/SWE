package IntegrationTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.*;
import UnitTest.utils.DBSeeder;
import ORM.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

public class BaseIntegrationTest {

    private static DBSeeder dbSeeder;
    protected static Connection connDAO; //connection used by DAOs
    private boolean teardownFail = false;


    private static void setTestSchema() throws SQLException {
        Statement stmt = connDAO.createStatement();
        stmt.execute("SET search_path TO test");
    }

    @BeforeAll
    public static void init() {
        try {
            dbSeeder = new DBSeeder();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to instantiate DBSeeder. Check connection to db");
        }
        dbSeeder.createTestSchema();
        assertDoesNotThrow(() -> dbSeeder.initDatabaseTest("db.sql", "data.sql"));
        try {
            connDAO = ConnectionManager.getInstance().getConnection();
            setTestSchema();
            connDAO.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Fail to get connection");
        }
    }

    @AfterEach
    public void teardown() {
        try {
            connDAO.rollback();
        } catch (SQLException e) {
            teardownFail = true;
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void teardownChecker() {
        assumeFalse(teardownFail, "Aborting test. DB state is inconsistent");
    }

    @AfterAll
    public static void connectionCloser(){
        try {
            dbSeeder.deleteTestSchema();
            connDAO.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
