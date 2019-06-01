package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {

    private String dbUrl = "jdbc:h2:tcp://localhost/~/hw2";
    private String name = "sa";
    private String password = "";

    public Connection connect() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}