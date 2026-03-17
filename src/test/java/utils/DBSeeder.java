package utils;

import ORM.ConnectionManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSeeder {

    private Connection connection;

    public DBSeeder() throws SQLException{
        this.connection = ConnectionManager.getInstance().getConnection();
    }

    public void createTestSchema() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS test AUTHORIZATION CURRENT_USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTestSchema(){
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP SCHEMA IF EXISTS test CASCADE");
            stmt.executeUpdate("SET search_path TO public");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initDatabaseTest(String filenameDDL, String filenameData){
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("SET search_path TO test");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error. Check if schema 'test' exists");
        }
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new FileSystemResource(filenameDDL), //file must be in root directory
                new FileSystemResource(filenameData)
        );
        populator.setContinueOnError(true);
        populator.populate(this.connection);
    }
}