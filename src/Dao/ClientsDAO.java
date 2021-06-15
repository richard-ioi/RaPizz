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


public class ClientsDAO {
        private  final Logger logger = Logger.getLogger(ClientsDAO.class);
        private  HashMap<Integer, Clients> cache= new HashMap<Integer, Clients>();

        /**
         * Returns Client that matches the id param.
         * @param id
         * @return
         */
        @SneakyThrows
        public Clients findClientsById(int id) {
            if(cache.containsKey(id)){
                logger.debug("Client in cache");
                return cache.get(id);
            }
            logger.debug("Finding clients");
            List<Clients> clients = find("SELECT * FROM Clients WHERE id_client= "+id);
            return clients.get(0);
        }

        /**
         * Finds customer that has spent the most.
         * @return the customer
         */
        @SneakyThrows
        public Clients findClientMostSpendings()/* throws SQLException*/ {
            List<Clients> clients = find("select * from Clients where depenses=(select max(depenses) from Clients);");
            logger.info("best client : " + clients.toString());
            return clients.get(0);
        }

    /**
     * General method used for select requests in Clients table
     * @param query
     * @return
     * @throws SQLException
     */
        @SneakyThrows
        public List<Clients> find(String query)throws SQLException {
            List<Clients> clientsList = new ArrayList<>();
            Statement statement = JdbcConnectDB.getConnection().createStatement();
            System.out.print(statement.toString());
            String sqlQuery = /*"SELECT * FROM Clients "+ */query;
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

    /**
     * Converts Result set into Clients object
     * @param resultSet
     * @return
     * @throws SQLException
     */
        private Clients resultSetToClients(ResultSet resultSet) throws SQLException{
            Clients clients = null;

            Integer id = resultSet.getInt("id_client");
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

            logger.info("get clients for order "+clients.toString());
            return clients;
        }

    }
