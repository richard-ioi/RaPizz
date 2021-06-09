package DataAccess;

import Dao.ClientsDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcConnectDB {
    private static String url = "jdbc:mysql://localhost:3306/RAPIZZ";
    private static String uname = "Admin";
    private static String password = "RichardEtEmilyMeritentUn20/20";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    private static Logger logger = Logger.getLogger(JdbcConnectDB.class);
    private JdbcConnectDB(){}

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = makeConnection();
        }
        return connection;
    }

    private static Connection makeConnection() throws SQLException {
        try {
            Class c = Class.forName(driver);
            // load the database driver class.
            connection = DriverManager.getConnection(url, uname, password);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
            logger.error("connection failed ", e);
            throw new SQLException(e);
        }
        return connection;
    }
    /* public static void main(String[] args) throws SQLException {
        String url = "jbdc:mysql:/RAPIZZ";
        String uname = "Admin";
        String password = "RichardEtEmilyMeritentUn20/20";

        try {
            Class c = Class.forName("com.mysql.jbdc.Driver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            Connection con = DriverManager.getConnection(url,uname,password);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }*/

}
