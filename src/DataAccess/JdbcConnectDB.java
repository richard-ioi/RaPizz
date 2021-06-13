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
import java.sql.*;


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
    public static String[][] execQueryDonneesBrutes(String query){
        try{
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int nbColumns = metaData.getColumnCount();
            logger.debug("Column nb : "+nbColumns);
            String[][] resultatRequete = new String[4][5];
            String[] columnNames = new String[nbColumns];
            for(int i=1; i< nbColumns; i++){
                columnNames[i] = metaData.getColumnName(i);
            }
            int rowCount =0;
            while(result.next()){
                for(int i=1; i<nbColumns; i++){
                    if(result.getObject(i) ==  null){
                        resultatRequete[rowCount][i]="null";
                    }
                    else{
                        resultatRequete[rowCount][i]=result.getObject(i).toString();
                    }
                    logger.debug(result.getObject(i).toString());

                }
                rowCount++;
            }
            //ihm.createDonneesBrutesJTable(resultatRequete, columnNames);
            return resultatRequete;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return new String[1][1];
    }

    /**
     *Converts Clients object data into 2D String Array to be passed to GUI
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
     *   Converts Livreur object data in 2D String Array to be passed to GUI
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
    public static void main(String[] arg) throws SQLException
    {
        BasicConfigurator.configure();
        //pizzaDAO.findPizzaById(2);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                logger.debug(execQueryDonneesBrutes("SELECT * FROM Clients")[i][j]);
            }
        }
        ihm.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }} );
        ihm.setVisible(true);

    }

}
