package connect;

import dao.AbstractDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {

    private String dbUrl = "jdbc:h2:tcp://localhost/~/hw2";
    private String name = "sa";
    private String password = "";

    private static final Logger logger = Logger.getLogger(DataBaseConnector.class);

    public Connection connect() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            logger.debug("Couldn't find correct driver", e);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, name, password);
        } catch (SQLException e) {
            logger.debug("Couldn't connect to Database");
        }
        return connection;
    }
}