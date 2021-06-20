package Dao;


import DataAccess.JdbcConnectDB;
import Domain.Commande;
import Domain.Menu;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuDAO {
    private static final Logger logger = Logger.getLogger(MenuDAO.class);
    private static HashMap<Integer, Menu> cache= new HashMap<Integer, Menu>();
    private PizzaDAO pizzaDAO = new PizzaDAO();

    /**
     * Finds ingredients in db.
     * @param pizzaId
     * @return
     */
    @SneakyThrows
    public List<Integer> findIngredients(int pizzaId)  {
        Statement statement = JdbcConnectDB.getConnection().createStatement();
        List<Integer> ingredients = new ArrayList<>();
        String sqlQuery = " SELECT id_ingredient FROM Pizza_Ingredients WHERE id_pizza ="+pizzaId;
        try {
                logger.debug("executing query : "+ sqlQuery);
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while(resultSet.next()){
                    ingredients.add(resultSet.getInt("id_ingredient"));
                    //logger.debug(ingredients);

                }
            } catch (SQLException sqlException){
                logger.error("error executing: "+sqlQuery, sqlException);
            } finally {
                if(statement != null) try {
                    statement.close();
                }catch (SQLException e){}

            }
        return ingredients;
    }

    /**
     * Creates list of pizza ingredients.
     * @return
     */
    public List<List<String>> convertIngredientIdToName(){
        List<List<String>> ingredients = new ArrayList<>();
        IngredientsDAO ingredientsDAO = new IngredientsDAO();

        for(int i=1; i<= pizzaDAO.pizzaCount(); i++){
            List<String> temp = new ArrayList<>();
            for(int j=0; j< findIngredients(i).size(); j++){
                temp.add(ingredientsDAO.findIngredientsById(this.findIngredients(i).get(j)).getNom());

            }
            ingredients.add(temp);
        }
        return ingredients;
    }

}
