package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final String SERVER_NAME = "localhost";
    private static final String PORT_NUMBER = "3306";
    private static String dbName = "testpersonne";
    private static String oldDbName = "testpersonne";
    private static Connection co;

    // creation de la connection
    public static Connection getConnection() throws SQLException {
        if(co == null || !dbName.equals(oldDbName)){
            Properties connectionProps = new Properties();
            connectionProps.put("user", USER_NAME);
            connectionProps.put("password", PASSWORD);
            String urlDB = "jdbc:mysql://" + SERVER_NAME + ":";
            urlDB += PORT_NUMBER + "/" + dbName;
            co = DriverManager.getConnection(urlDB, connectionProps);
            oldDbName = dbName;
        }
        return co;
    }

    public static void setNomDB(String nomDB) throws SQLException {
        dbName = nomDB;
        co = getConnection();
    }
}
