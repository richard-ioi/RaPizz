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

    /**
     * Gets the total income from orders
     * @return
     */
    @SneakyThrows
    public double profits(){
        double profits = 0;
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = "SELECT SUM(prix) FROM Commande";
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                profits = resultSet.getDouble("SUM(prix)");
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return profits;
    }

    @SneakyThrows
    public List<Commande> getAllCommande(){
        List<Commande> commande = find("SELECT * FROM Commande");
        return commande;
    }

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
