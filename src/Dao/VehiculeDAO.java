package Dao;

import DataAccess.JdbcConnectDB;
import Domain.Vehicule;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VehiculeDAO {
    private static final Logger logger = Logger.getLogger(VehiculeDAO.class);
    private static HashMap<Integer, Vehicule> cache= new HashMap<Integer, Vehicule>();

    /**
     * Returns Vehicule that matches the id param.
     * @param id
     * @return
     */
    public Vehicule findVehiculeById(int id){
        if(cache.containsKey(id)){
            return cache.get(id);
        }
        List<Vehicule> vehicule = find("WHERE id_vehicule = "+id);
        return vehicule.get(0);
    }

    @SneakyThrows
    public List<Integer> getVehiculeFromTable(String table){
        Statement statement = JdbcConnectDB.getConnection().createStatement();
        List<Integer> vehiculeList = new ArrayList<>();
        String sqlQuery = "SELECT id_vehicule FROM "+table ;
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                vehiculeList.add(resultSet.getInt("id_vehicule"));
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return vehiculeList;
    }

    public String getVehiculeNoUse(){
        List<Integer> commandeVehicule = getVehiculeFromTable("Commande");
        List<Integer> vehiculeVehicule = getVehiculeFromTable("Vehicule");
        String name ="";
        for(int i=0; i<Math.max(commandeVehicule.size(), vehiculeVehicule.size())-1; i++){
            if(commandeVehicule.contains(vehiculeVehicule.get(i))){
                continue;
            }
            else{
                name = findVehiculeById(vehiculeVehicule.get(i)).getImmatriculation();
            }
        }
        if(name.equals("")) name = "Tous utilisés";
        return name;
    }



    @SneakyThrows
    public List<Vehicule> find(String query) /*throws SQLException*/ {
        List<Vehicule> vehiculeList = new ArrayList<>();
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = "SELECT * FROM Vehicule "+ query;
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                Vehicule vehicule = resultSetToVehicule(resultSet);
                vehiculeList.add(vehicule);
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return vehiculeList;
    }
    private Vehicule resultSetToVehicule(ResultSet resultSet) throws SQLException{
        Vehicule vehicule = null;

        Integer id = resultSet.getInt("id_vehicule");
        if(cache.containsKey(id)) vehicule =cache.get(id);
        else vehicule = new Vehicule();

        vehicule.setIdVehicule(id);
        vehicule.setImmatriculation(resultSet.getString("immatriculation"));
        vehicule.setType(resultSet.getString("type"));

        if(! cache.containsKey(id)) cache.put(id, vehicule);

        logger.info("get vehicule for order "+vehicule.getIdVehicule());
        return vehicule;
    }
}
