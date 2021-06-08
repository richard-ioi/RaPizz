package Dao;

import DataAccess.JdbcConnectDB;
import Domain.Clients;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClientsDAO implements Dao<Clients>{
    private static final Logger logger = Logger.getLogger(ClientsDAO.class);
    private static HashMap<Integer, Clients> cache= new HashMap<Integer, Clients>();

    /**
     * Returns Client that matches the id param.
     * @param id
     * @return
     */
    public Clients findClientsById(int id){
        if(cache.containsKey(id)){
            return cache.get(id);
        }
        List<Clients> clients = find("WHERE id_clients = "+id);
        return clients.get(0);
    }

    @SneakyThrows
    public List<Clients> find(String query) /*throws SQLException*/ {
        List<Clients> clientsList = new ArrayList<Clients>();
        Statement statement = JdbcConnectDB.getConnection().createStatement();

        String sqlQuery = "SELECT * FROM Clients "+ query;
        try {
            logger.debug("executing query : "+ sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while( resultSet.next()){
                Clients clients = resultSetToClients(resultSet);
                clientsList.add(clients);
            }
        } catch (SQLException sqlException){
            logger.error("error executing: "+sqlQuery, sqlException);
        } finally {
            if(statement != null) try {
                statement.close();
            }catch (SQLException e){}

        }
        return clientsList;
    }

    private Clients resultSetToClients(ResultSet resultSet) throws SQLException{
        Clients clients = null;

        Integer id = resultSet.getInt("id_clients");
        if(cache.containsKey(id)) clients =cache.get(id);
        else clients = new Clients();

        clients.setIdClient(id);
        clients.setAdresse(resultSet.getString("adresse"));
        clients.setDepenses(resultSet.getFloat("depenses"));
        clients.setNom(resultSet.getString("nom"));
        clients.setPizzaAchete(resultSet.getInt("pizza_achete"));
        clients.setPrenom(resultSet.getString("prenom"));
        clients.setSolde(resultSet.getFloat("solde"));
        clients.setTelephone(resultSet.getString("telephone"));

        if(! cache.containsKey(id)) cache.put(id, clients);

        logger.info("get clients for order "+clients.getNom());
        return clients;
    }


}
