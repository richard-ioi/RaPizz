package DataAccess;

import Dao.*;
import Domain.Livreur;
import Graphics.IHM;
import org.apache.log4j.BasicConfigurator;
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
    private static IHM IHM=new IHM(800,800);
    private static String url = "jdbc:mysql://localhost:3306/RAPIZZ";
    private static String uname = "Admin";
    private static String password = "RichardEtEmilyMeritentUn20/20";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;


    //Fonction de test
    /*public static void selectClient(int ID){
        System.out.println(ID);
        String[][] cellules=new String[1][1];
        cellules[0][0]="LOL TEST";
        String[] columnNames=new String[1];
        columnNames[0]="NOM DE COLONNE TEST";

        IHM.createCommandesJTable(cellules, columnNames);
    }*/
    /**
     * Clients DAO
     */
    private static ClientsDAO clientsDAO = new ClientsDAO();

    /**
     * Livreur DAO
     */
    private static LivreurDAO livreurDAO = new LivreurDAO();

    /**
     * Ingredients DAO
     */
    private static IngredientsDAO ingredientsDAO = new IngredientsDAO();

    /**
     * Commande DAO
     */
    private static CommandeDAO commandeDAO = new CommandeDAO();

    /**
     * Pizza DAO
     */
    private static PizzaDAO pizzaDAO = new PizzaDAO();

    /**
     *
     */
    private static VehiculeDAO vehiculeDAO = new VehiculeDAO();

    private static Logger logger = Logger.getLogger(JdbcConnectDB.class);
    public JdbcConnectDB(){}

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = makeConnection();
            logger.info("Connection Made");
        }
        return connection;
    }

    private static Connection makeConnection() throws SQLException {
        /*try {
            Class c = Class.forName(driver);
            // load the database driver class.
            connection = DriverManager.getConnection(url, uname, password);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
            logger.error("connection failed : ", e);
            throw new SQLException(e);
        }*/
        return connection;
    }

    public static void closeStatement(Statement statement) throws SQLException {
        statement.close();
    }

    public static void printTest(){
        System.out.println("TEST");
    }

    /**
     *
     * @param id
     */
    public static String[][] getLivreurById(int id){
        Livreur livreur = livreurDAO.findLivreurById(id);
        String[][] livreurString = new String[0][];
        livreurString[0][0] = ""+livreur.getIdLivreur();
        livreurString[1][0] = livreur.getNom();
        livreurString[2][0] = livreur.getPrenom();
        livreurString[3][0] = ""+livreur.getRetards();

        return livreurString;
    }
    public static void main(String[] arg) throws SQLException
    {
        BasicConfigurator.configure();
        //pizzaDAO.findPizzaById(2);
        //pizzaDAO.findMenuValue();
        IHM.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }} );
        IHM.setVisible(true);

    }

}
