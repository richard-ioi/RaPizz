package Dao;

import DataAccess.JdbcConnectDB;
import Domain.Commande;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandeDAO {

    private static final Logger logger = Logger.getLogger(CommandeDAO.class);
    private static HashMap<Integer, Commande> cache= new HashMap<Integer, Commande>();

    /**
     * Returns Commande that matches the id param.
     * @param id
     * @return
     */
    public Commande findCommandeById(int id) throws SQLException {
        if(cache.containsKey(id)){
            return cache.get(id);
        }
        List<Commande> commande = find("SELECT * FROM Commande WHERE id_commande = "+id);
        return commande.get(0);
    }
    public void updateCommandePrice() throws SQLException {
        updateCommande("UPDATE Commande " +
                "INNER JOIN Pizza ON Commande.id_pizza=Pizza.id_pizza " +
                "SET Commande.prix = CASE " +
                "WHEN Commande.taille=\"humaine\" THEN Pizza.prix " +
                "                        WHEN Commande.taille=\"naine\" THEN Pizza.prix-(Pizza.prix*(1/3)) " +
                "                        WHEN Commande.taille=\"ogresse\" THEN Pizza.prix+(Pizza.prix*(1/3)) " +
                "                        ELSE Commande.prix " +
                "                        END " +
                "WHERE Commande.taille in (\"humaine\",\"naine\",\"ogresse\");");
        //List<Commande> commande = find("SELECT * FROM Commande");
        //return commande.get(0);
    }

/*    public String[][] getAllCommandes(){
        String query =
                "select \n" +
                "    t2.nom, t3.nom, t4.nom, t5.type, t1.prix, t1.taille, t1.depart_livraison, t1.arrive_livraison\n" +
                "from\n" +
                "    Commande as t1\n" +
                "    left join Pizza as t2 on t1.id_pizza = t2.id_pizza\n" +
                "    left join Clients as t3 on t1.id_client = t3.id_client\n" +
                "    left join Livreur as t4 on t1.id_livreur = t4.id_livreur\n" +
                "    left join Vehicule as t5 on t1.id_vehicule=t5.id_vehicule";
        try{
            Statement statement = JdbcConnectDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int nbColumns = metaData.getColumnCount();
            int nbRows = countRows(query);
            //logger.debug("Column nb : "+nbColumns);
            String[][] commandeList = new String[nbRows][nbColumns];
            String[] columnNames = new String[nbColumns];
            for(int i=1; i< nbColumns; i++){
                columnNames[i] = metaData.getColumnName(i);
            }
            int rowCount =0;
            while(result.next() && rowCount!=nbRows){
                for(int i=1; i<=nbColumns; i++) {
                    if (result.getObject(i).equals(null)) {
                        logger.debug("is null");
                        commandeList[rowCount][i-1] = "null";
                    } else {
                        commandeList[rowCount][i-1] = result.getObject(i).toString();
                    }

                }
                rowCount++;
            }
            return commandeList;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return new String[1][1];
    }*/
    @SneakyThrows
    public List<Commande> getAllCommande(){
        List<Commande> commande = find("SELECT * FROM Commande");
        return commande;
    }
/*    private int countRows(String query) {
        try{
            Statement statement = JdbcConnectDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery(query);
            result.last();
            int nbRows = result.getRow();
            result.first();
            return nbRows;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }*/



    public List<Commande> find(String query) throws SQLException {
        List<Commande> commandeList = new ArrayList<>();
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = query;
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                Commande commande = resultSetToCommande(resultSet);
                commandeList.add(commande);
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return commandeList;
    }

    public void updateCommande(String query) throws SQLException {
        List<Commande> commandeList = new ArrayList<>() ;
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = query;
        try {
            logger.debug("executing update : "+ sqlQuery);
            statement.executeUpdate(sqlQuery);
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
    }
    //public Commande
    private Commande resultSetToCommande(ResultSet resultSet) throws SQLException{
        Commande commande = null;

        Integer id = resultSet.getInt("id_commande");
        if(cache.containsKey(id)) commande =cache.get(id);
        else commande = new Commande();

        commande.setIdCommande(id);
        commande.setDepartLivraison(resultSet.getTimestamp("depart_livraison"));
        commande.setArriveLivraison(resultSet.getTimestamp("arrive_livraison"));
        commande.setIdClient(resultSet.getInt("id_client"));
        commande.setIdLivreur(resultSet.getInt("id_livreur"));
        commande.setIdPizza(resultSet.getInt("id_pizza"));
        commande.setIdVehicule(resultSet.getInt("id_vehicule"));
        commande.setPrix(resultSet.getFloat("prix"));
        commande.setTaille(resultSet.getString("taille"));

        if(! cache.containsKey(id)) cache.put(id, commande);

        logger.info("get commande for order "+commande.getIdCommande());
        return commande;
    }
}
