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
        List<Pizza> pizza = find("WHERE id_pizza = "+id);
        return pizza.get(0);
    }

    @SneakyThrows
    public List<Pizza> find(String query) /*throws SQLException*/ {
        List<Pizza> pizzaList = new ArrayList<>();
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = "SELECT * FROM Pizza "+ query;
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

        if(! cache.containsKey(id)) cache.put(id, pizza);

        logger.info("get pizza for order "+pizza.getNom());
        return pizza;
    }
}
