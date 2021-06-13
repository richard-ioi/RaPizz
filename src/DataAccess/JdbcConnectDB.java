package DataAccess;

import Dao.*;
import Domain.Clients;
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

    private static IHM ihm=new IHM(800,800);

    private static String url = "jdbc:mysql://localhost:3306/RAPIZZ";
    private static String uname = "Admin";
    private static String password = "RichardEtEmilyMeritentUn20/20";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;


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
     *Converts Clients object data into 2D String Array to be passed to GUI
     * @param id
     */
    public static void getClientById(int id){
        Clients clients = clientsDAO.findClientsById(id);
        String[][] clientString = new String[8][1];
        clientString[0][0] = ""+clients.getIdClient();
        clientString[1][0] = clients.getNom();
        clientString[2][0] = clients.getPrenom();
        clientString[3][0] = clients.getAdresse();
        clientString[4][0] = clients.getTelephone();
        clientString[5][0] = ""+clients.getSolde();
        clientString[6][0] = ""+clients.getPizzaAchete();
        clientString[7][0] = ""+clients.getDepenses();

        ihm.createCommandesJTable(clientString, new String[]{"id_client", "nom", "prenom", "adresse", "telephone",
         "solde", "pizza_achete","depenses"});
    }

    /**
     *   Converts Livreur object data in 2D String Array to be passed to GUI
     * @param id
     */
    public static void getLivreurById(int id){
        Livreur livreur = livreurDAO.findLivreurById(id);
        String[][] livreurString = new String[4][1];
        livreurString[0][0] = ""+livreur.getIdLivreur();
        livreurString[1][0] = livreur.getNom().toString();
        livreurString[2][0] = livreur.getPrenom().toString();
        livreurString[3][0] = ""+livreur.getRetards();

        ihm.createCommandesJTable(livreurString, new String[]{"id_livreur", "nom", "prenom", "retards"});
    }
    public static void main(String[] arg) throws SQLException
    {
        BasicConfigurator.configure();
        //pizzaDAO.findPizzaById(2);

        //pizzaDAO.findMenuValue();
        ihm.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }} );
        ihm.setVisible(true);

    }

}
