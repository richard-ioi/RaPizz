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
        List<Pizza> price = find("SELECT prix FROM PIZZA WHERE id_pizza="+idPizza);
        double prix = (double)price.get(0).getPrix();
        HashMap<String, Double> prixTaille = new HashMap();
        prixTaille.put("naine", prix-prix*0.33);
        prixTaille.put("humaine", prix);
        prixTaille.put("ogresse", prix+prix*0.33);
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
