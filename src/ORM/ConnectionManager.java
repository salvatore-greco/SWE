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
    private final Connection connection;
    private static ConnectionManager instance = null;

    private ConnectionManager() throws SQLException, SQLTimeoutException {
        Properties props = getDBProprieties();
        connection = DriverManager.getConnection(props.getProperty("url"), props);
    }

    public static ConnectionManager getInstance() throws SQLException, SQLTimeoutException{
        if (instance == null) instance = new ConnectionManager();
        return instance;
    }

    public Connection getConnection(){
        if (instance == null){
            throw new IllegalStateException("getConnection must be called after getInstance");
        }
        return connection;
    }

    private Properties getDBProprieties(){
        Dotenv dotenv = Dotenv.load();
        Properties props = new Properties();
        props.setProperty("url", dotenv.get("DB_URL"));
        props.setProperty("user", dotenv.get("DB_USER"));
        props.setProperty("password", dotenv.get("DB_PASS"));
        return props;
    }
}
