package DataAccess;

import Dao.*;
import Domain.Clients;
import Domain.Commande;
import Domain.Livreur;
import Graphics.IHM;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcConnectDB {

    private static IHM ihm=new IHM(800,800);

    private static String url = "jdbc:mysql://localhost:3306/RAPIZZ";
    private static String uname = "Admin";
    private static String password = "RichardEtEmilyMeritentUn20/20";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;


    /**
     * Clients DAO.
     */
    private static ClientsDAO clientsDAO = new ClientsDAO();

    /**
     * Livreur DAO.
     */
    private static LivreurDAO livreurDAO = new LivreurDAO();

    /**
     * Ingredients DAO.
     */
    private static IngredientsDAO ingredientsDAO = new IngredientsDAO();

    /**
     * Commande DAO.
     */
    private static CommandeDAO commandeDAO = new CommandeDAO();

    /**
     * Pizza DAO.
     */
    private static PizzaDAO pizzaDAO = new PizzaDAO();

    /**
     *  Vehicule DAO.
     */
    private static VehiculeDAO vehiculeDAO = new VehiculeDAO();

    /**
     * Menu DAO.
     */
    private static MenuDAO menuDAO = new MenuDAO();

    /**
     * Log4J logger
     */
    private static Logger logger = Logger.getLogger(JdbcConnectDB.class);

    /**
     * Constructor
     */
    public JdbcConnectDB(){}

    /**
     * Gets database connection.
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = makeConnection();
            logger.info("Connection Made");
        }
        return connection;
    }

    /**
     * Makes connection with database.
     * @return
     * @throws SQLException
     */
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

    /**
     * Closes an open statement.
     * @param statement
     * @throws SQLException
     */
    public static void closeStatement(Statement statement) throws SQLException {
        statement.close();
    }


    /**
     * Counts rows in a query response.
     * @param query
     * @return
     */
    public static int countRows(String query){
        try{
            connection = getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery(query);
            result.last();
            int nbRows = result.getRow();
            result.first();
            return nbRows;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    /**
     * Execute random query in GUI.
     * @param query
     */
    public static void execQueryDonneesBrutes(String query){
        try{
            connection = getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int nbColumns = metaData.getColumnCount();
            int nbRows = countRows(query);
            //logger.debug("Column nb : "+nbColumns);
            String[][] resultatRequete = new String[nbRows][nbColumns];
            String[] columnNames = new String[nbColumns];
            for(int i=1; i< nbColumns; i++){
                columnNames[i] = metaData.getColumnName(i);
            }
            int rowCount =0;
            while(result.next() && rowCount!=nbRows){
                for(int i=1; i<=nbColumns; i++) {
                        if (result.getObject(i).equals(null)) {
                            logger.debug("is null");
                            resultatRequete[rowCount][i-1] = "null";
                        } else {
                            resultatRequete[rowCount][i-1] = result.getObject(i).toString();
                        }

                }
                rowCount++;
            }
            ihm.createDonneesBrutesJTable(resultatRequete, columnNames);
            //return resultatRequete;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        //return new String[1][1];
    }

    /**
     * Sends the best Client (with highest spendings) and the worst Livreur (most late delievries) to the GUI.
     */
    public static void getBestClientWorseLivreur(){
        Clients client = clientsDAO.findClientMostSpendings();
        Livreur livreur = livreurDAO.findWorseDeliveryGuy();
        String livreurString = livreur.getPrenom()+ " " +livreur.getNom();
        String clientString = client.getPrenom()+" "+client.getNom();
        ihm.createCommandesStatsPanel(livreurString, clientString);
        //return new String[]{clientString, livreurString};
    }

    /**
     *Converts Clients object data into 2D String Array to be passed to GUI.
     * @param id
     */
    public static void getClientById(int id){
        Clients clients = clientsDAO.findClientsById(id);
        String[][] clientString = new String[1][8];
        clientString[0][0] = ""+clients.getIdClient();
        clientString[0][1] = clients.getNom();
        clientString[0][2] = clients.getPrenom();
        clientString[0][3] = clients.getAdresse();
        clientString[0][4] = clients.getTelephone();
        clientString[0][5] = ""+clients.getSolde();
        clientString[0][6] = ""+clients.getPizzaAchete();
        clientString[0][7] = ""+clients.getDepenses();

        ihm.createCommandesJTable(clientString, new String[]{"id_client", "nom", "prenom", "adresse", "telephone",
         "solde", "pizza_achete","depenses"});
    }

    /**
     *   Converts Livreur object data in 2D String Array to be passed to GUI.
     * @param id
     */
    public static void getLivreurById(int id){
        Livreur livreur = livreurDAO.findLivreurById(id);
        String[][] livreurString = new String[1][4];
        livreurString[0][0] = ""+livreur.getIdLivreur();
        livreurString[0][1] = livreur.getNom();
        livreurString[0][2] = livreur.getPrenom();
        livreurString[0][3] = ""+livreur.getRetards();

        ihm.createCommandesJTable(livreurString, new String[]{"id_livreur", "nom", "prenom", "retards"});
    }

    /**
     * Gets All orders for display
     */
    public static void getAllCommandes(){
        int nbRow = countRows("SELECT * FROM Commande");
        List<Commande> commandeList = commandeDAO.getAllCommande();
        for(int i=0; i<nbRow; i++){
            ihm.createCommandesInnerPanel(
                    commandeList.get(i).getIdCommande(),
                    commandeList.get(i).getIdClient(),
                    clientsDAO.findClientsById(commandeList.get(i).getIdClient()).getNom(),
                    commandeList.get(i).getIdPizza(),
                    pizzaDAO.findPizzaById(commandeList.get(i).getIdPizza()).getNom(),
                    commandeList.get(i).getIdLivreur(),
                    livreurDAO.findLivreurById(commandeList.get(i).getIdLivreur()).getNom(),
                    commandeList.get(i).getDepartLivraison().toString(),
                    commandeList.get(i).getArriveLivraison().toString()
                    );
        }
        ihm.reBuildIHM();
    }

    /**
     * Gets all pizzas for menu GUI
     */
    public static void getAllPizza(){
        for(int i=1; i<= pizzaDAO.pizzaCount(); i++){
            ihm.createMenuInnerPanel(pizzaDAO.findPizzaById(i).getNom(),
                    menuDAO.convertIngredientIdToName().get(i-1).toString(), 1, 1, 1);
        }
        ihm.reBuildIHM();
    }
    /**
     * Application main method, sets all the data and calls start up methods.
     * @param arg
     * @throws SQLException
     */
    public static void main(String[] arg) throws SQLException
    {
        BasicConfigurator.configure();
        commandeDAO.updateCommandePrice();
        getBestClientWorseLivreur();
        getAllCommandes();
        getAllPizza();
        //logger.debug(menuDAO.convertIngredientIdToName());

        // faire fonction qui itere n fois la fonction de richard pour
        ihm.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }} );
        ihm.setVisible(true);

    }

}
