package Dao;

import DataAccess.JdbcConnectDB;
import Domain.Ingredients;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientsDAO {

    private static final Logger logger = Logger.getLogger(IngredientsDAO.class);
    private static HashMap<Integer, Ingredients> cache= new HashMap<Integer, Ingredients>();

    /**
     * Returns Ingredients that matches the id param.
     * @param id
     * @return
     */

    public Ingredients findIngredientsById(int id){
        if(cache.containsKey(id)){
            return cache.get(id);
        }
        List<Ingredients> ingredients = find("WHERE id_ingredient = "+id);
        return ingredients.get(0);
    }

    @SneakyThrows
    public List<Ingredients> find(String query) /*throws SQLException*/ {
        List<Ingredients> ingredientsList = new ArrayList<>();
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = "SELECT * FROM Ingredients "+ query;
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                Ingredients ingredients = resultSetToIngredients(resultSet);
                ingredientsList.add(ingredients);
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return ingredientsList;
    }
    private Ingredients resultSetToIngredients(ResultSet resultSet) throws SQLException{
        Ingredients ingredients = null;

        Integer id = resultSet.getInt("id_ingredient");
        if(cache.containsKey(id)) ingredients =cache.get(id);
        else ingredients = new Ingredients();

        ingredients.setIdIngredients(id);
        ingredients.setNom(resultSet.getString("nom"));
        ingredients.setType(resultSet.getString("type"));

        if(! cache.containsKey(id)) cache.put(id, ingredients);

        logger.info("get ingredients for order "+ingredients.getNom());
        return ingredients;
    }
}
