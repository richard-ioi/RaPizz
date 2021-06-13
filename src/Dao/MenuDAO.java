package Dao;


import DataAccess.JdbcConnectDB;
import Domain.Commande;
import Domain.Menu;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class MenuDAO {
    private static final Logger logger = Logger.getLogger(MenuDAO.class);
    private static HashMap<Integer, Menu> cache= new HashMap<Integer, Menu>();

    /*public void findIngredients() throws SQLException {
        Statement statement = JdbcConnectDB.getConnection().createStatement();
        HashMap<String, String> pizzaIngredients = new HashMap<>();
        String sqlQuery = "SELECT Ingredients.nom, Pizza.nom FROM Ingredients join Pizza_Ingredients ON Ingredients.id_ingredient = Pizza_Ingredients.id_ingredient join Pizza ON Pizza_Ingredients.id_pizza = Pizza.id_pizza;";
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            int rowCount = 0;
            while(resultSet.next() && rowCount!=nbRows){
                for(int i=1; i<=nbColumns; i++) {
                    if (resultSet.getObject(i).equals(null)) {
                        logger.debug("is null");
                        resultatRequete[rowCount][i-1] = "null";
                    } else {
                        resultatRequete[rowCount][i-1] = resultSet.getObject(i).toString();
                    }

                }
                rowCount++;
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
    }*/
    private Menu resultSetToCommande(ResultSet resultSet) throws SQLException {
        Menu menu = null;

        Integer id = resultSet.getInt("id_commande");
        if(cache.containsKey(id)) menu =cache.get(id);
        else menu = new Menu();

        /*commande.setIdCommande(id);
        commande.setDepartLivraison(resultSet.getTimestamp("depart_livraison"));
        commande.setArriveLivraison(resultSet.getTimestamp("arrive_livraison"));
        commande.setIdClient(resultSet.getInt("id_client"));
        commande.setIdLivreur(resultSet.getInt("id_livreur"));
        commande.setIdPizza(resultSet.getInt("id_pizza"));
        commande.setIdVehicule(resultSet.getInt("id_vehicule"));
        commande.setPrix(resultSet.getFloat("prix"));
        commande.setTaille(resultSet.getString("taille"));*/

        if(! cache.containsKey(id)) cache.put(id, menu);

        logger.info("get commande for order "+menu);
        return menu;
    }
}
