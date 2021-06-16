package Dao;

import DataAccess.JdbcConnectDB;
import Domain.Pizza;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PizzaDAO {
    private static final Logger logger = Logger.getLogger(PizzaDAO.class);
    private static HashMap<Integer, Pizza> cache= new HashMap<Integer, Pizza>();

    /**
     * Returns Pizza that matches the id param.
     * @param id
     * @return
     */
    public Pizza findPizzaById(int id){
        if(cache.containsKey(id)){
            return cache.get(id);
        }
        List<Pizza> pizza = find("SELECT * FROM Pizza WHERE id_pizza = "+id);
        return pizza.get(0);
    }

    public Pizza findMenuValue(){
        List<Pizza> pizzaList = find("SELECT * FROM Ingredients JOIN Pizza_Ingredients " +
                "ON Ingredients.id_ingredient = Pizza_Ingredient.id_ingredient " +
                "JOIN Pizza " +
                "ON Pizza.id_pizza=Pizza_Ingredient.id_pizza " +
                "WHERE Pizza.nom = 'insegnamento'");
        return pizzaList.get(0);

    }
    public int pizzaCount(){
        try{
            Statement statement = JdbcConnectDB.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery("SELECT * FROM Pizza");
            result.last();
            int nbRows = result.getRow();
            result.first();
            return nbRows;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }
    public HashMap<String, Double> getPizzaPrice(int idPizza){
        double prix = (double)findPizzaById(idPizza).getPrix();
        HashMap<String, Double> prixTaille = new HashMap();
        prixTaille.put("naine", (double)Math.round((prix-prix*0.33)*100)/100);
        prixTaille.put("humaine", prix);
        prixTaille.put("ogresse", (double)Math.round((prix+prix*0.33)*100)/100);
        return prixTaille;
    }
    @SneakyThrows
    public List<Pizza> find(String query) /*throws SQLException*/ {
        List<Pizza> pizzaList = new ArrayList<>();
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = query;
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                Pizza pizza = resultSetToPizza(resultSet);
                pizzaList.add(pizza);
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return pizzaList;
    }

    @SneakyThrows
    public int popularId(String order){
        int pizza = 1;
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = "SELECT COUNT(id_pizza) AS c FROM Commande GROUP BY id_pizza ORDER BY c "+ order+" LIMIT 1";
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                pizza = resultSet.getInt("c");
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return pizza;
    }
    @SneakyThrows
    public Pizza mostPopularPizza(){
        return findPizzaById(popularId("DESC"));
    }

    public String leastPopularPizza(){
        if(findPizzaById(popularId("ASC")) != mostPopularPizza()){
            return findPizzaById(popularId("ASC")).getNom();
        }
        return null;
    }

    private Pizza resultSetToPizza(ResultSet resultSet) throws SQLException{
        Pizza pizza = null;

        Integer id = resultSet.getInt("id_pizza");
        if(cache.containsKey(id)) pizza =cache.get(id);
        else pizza = new Pizza();

        pizza.setIdPizza(id);
        pizza.setNom(resultSet.getString("nom"));
        pizza.setPrix(resultSet.getInt("prix"));
        //pizza.setIngredientsList(resultSet.getArray(""));

        if(! cache.containsKey(id)) cache.put(id, pizza);

        logger.info("get pizza for order "+pizza);
        return pizza;
    }
}
