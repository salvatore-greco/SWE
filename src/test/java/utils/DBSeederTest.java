package utils;

import ORM.ConnectionManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DBSeederTest {
    private static DBSeeder dbSeeder;

    @BeforeAll
    public static void init() {
        try {
            dbSeeder = new DBSeeder();
        } catch (SQLException e) {
            throw new RuntimeException("something went wrong creating test connection");
        }
    }

    @Test
    @Order(1)
    public void DBSeeder_createSchema() {
        dbSeeder.createTestSchema();
        try {
            Connection conn = ConnectionManager.getInstance().getConnectionTestSchema();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_namespace where nspname = 'test'");
            if (rs.next())
                assertEquals(1, rs.getInt(1));
            else
                fail("Schema does not exist");
        } catch (SQLException e) {
            fail("Error while accessing database");
        }
    }

    @Test
    @Order(2)
    public void DBSeeder_fillDB(){
        //purtroppo non posso dividere i test perchè faccio tutto dentro initDatabaseTest
        //perché correttamente deve farlo tutto là (settare lo schema a test e aggiungere le tabelle)
        dbSeeder.initDatabaseTest("db.sql", "data.sql");
        //testing schema search path
        try{
            Connection connection = ConnectionManager.getInstance().getConnectionTestSchema();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW search_path");
            if(rs.next()){
                assertEquals("test", rs.getString("search_path"));
            }
        }
        catch (SQLException e){
            fail("Error while accessing database when testing search path");
        }
        // testing ddl and insert into
        try{
            Connection connection = ConnectionManager.getInstance().getConnectionTestSchema();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT email from \"user\" where email='prova@email.com'");
            if(rs.next()){
                assertEquals("prova@email.com", rs.getString("email"));
            }
        }
        catch (SQLException e){
            fail("Error while accessing database when testing  ddl and insert into");
        }
    }


    @Test
    @Order(3)
    public void DBSeeder_deleteSchema() {
        dbSeeder.deleteTestSchema();
        try {
            Connection conn = ConnectionManager.getInstance().getConnectionTestSchema();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_namespace where nspname = 'test'");
            if (rs.next())
                fail("Schema still exist");
            else
                return; //Source: https://stackoverflow.com/questions/4036144/junit4-fail-is-here-but-where-is-pass
        } catch (SQLException e) {
            fail("Error while accessing database");
        }
    }
}
