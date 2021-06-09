package Dao;

import DataAccess.JdbcConnectDB;
import Domain.Livreur;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LivreurDAO {
    private static final Logger logger = Logger.getLogger(LivreurDAO.class);
    private static HashMap<Integer, Livreur> cache= new HashMap<Integer, Livreur>();

    /**
     * Returns Livreur that matches the id param.
     * @param id
     * @return
     */
    public Livreur findLivreurById(int id){
        if(cache.containsKey(id)){
            return cache.get(id);
        }
        List<Livreur> livreur = find("WHERE id_livreur = "+id);
        return livreur.get(0);
    }

    @SneakyThrows
    public List<Livreur> find(String query) /*throws SQLException*/ {
        List<Livreur> livreurList = new ArrayList<>();
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = "SELECT * FROM Livreur "+ query;
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                Livreur livreur = resultSetToLivreur(resultSet);
                livreurList.add(livreur);
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return livreurList;
    }
    private Livreur resultSetToLivreur(ResultSet resultSet) throws SQLException{
        Livreur livreur = null;

        Integer id = resultSet.getInt("id_livreur");
        if(cache.containsKey(id)) livreur =cache.get(id);
        else livreur = new Livreur();

        livreur.setIdLivreur(id);
        livreur.setNom(resultSet.getString("nom"));
        livreur.setPrenom(resultSet.getString("prenom"));
        livreur.setRetards(resultSet.getInt("retards"));

        if(! cache.containsKey(id)) cache.put(id, livreur);

        logger.info("get livreur for order "+livreur.getNom());
        return livreur;
    }
}
