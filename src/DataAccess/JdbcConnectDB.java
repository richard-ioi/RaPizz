package DataAccess;

import Dao.ClientsDAO;
import Graphics.IHM;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcConnectDB {
    private static JFrame IHM=new IHM(800,800);
    private static String url = "jdbc:mysql://localhost:3306/RAPIZZ";
    private static String uname = "Admin";
    private static String password = "RichardEtEmilyMeritentUn20/20";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    private static Logger logger = Logger.getLogger(JdbcConnectDB.class);
    public JdbcConnectDB(){}

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
            logger.error("connection failed : ", e);
            throw new SQLException(e);
        }
        return connection;
    }

    public static void closeStatement(Statement statement) throws SQLException {
        statement.close();
    }

    public static void printTest(){
        System.out.println("TEST");
    }

    public static void main(String[] arg) throws SQLException
    {
        IHM.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }} );
        IHM.setVisible(true);
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
