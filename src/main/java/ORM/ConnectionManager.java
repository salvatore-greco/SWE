package ORM;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Properties;


/*
    ConnectionManager è un singleton/deve esistere una sola istanza di esso.
    Focus: mi serve creare il driver manager e la connessione. DriverManager.getConnection(url, user, pass) mi connette al db
    Io poi passerò il connection manager ai vari dao. Essi creeranno PreparedStatement/Statement stmt -> stmt.executeQuery(query)
    esso mi ritorna un ResultSet con i dati provenienti dal DB. Cerchiamo di fornire meglio possibile i dati come le vogliono le classi
    (evitiamo di ritornare il ResultSet grezzo)

 */
public class ConnectionManager {
    private Connection connection;
    private Connection connectionTestSchema = null; //un'altra connessione perchè poi dovrò settare autoCommit = false
    private static ConnectionManager instance = null;

    private ConnectionManager() throws SQLException, SQLTimeoutException {
        this.connection = initializeConnection();
    }

    private Connection initializeConnection() throws SQLException {
        Connection connection;
        Properties props = getDBProprieties();
        connection = DriverManager.getConnection(props.getProperty("url"), props);
        return connection;
    }

    public static ConnectionManager getInstance() throws SQLException, SQLTimeoutException{
        if (instance == null) instance = new ConnectionManager();
        return instance;
    }

    public Connection getConnection(){
        if (instance == null){
            throw new IllegalStateException("getConnection must be called after getInstance");
        }
        try {
            if (connection.isClosed()){
                this.connection = initializeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnectionTestSchema(){
        if (instance == null){
            throw new IllegalStateException("getConnection must be called after getInstance");
        }
        try {
            if (connectionTestSchema == null)
                connectionTestSchema = initializeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionTestSchema;
    }
    private Properties getDBProprieties(){
        Dotenv dotenv = Dotenv.configure().directory("./src").load();
        Properties props = new Properties();
        props.setProperty("url", dotenv.get("DB_URL"));
        props.setProperty("user", dotenv.get("DB_USER"));
        props.setProperty("password", dotenv.get("DB_PASS"));
        return props;
    }
    public String getDBUsername(){
        return getDBProprieties().getProperty("user");
    }
}
