package Dao;

import Domain.Clients;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class ClientsDAO {
    private static final Logger logger = Logger.getLogger(ClientsDAO.class);
    private static HashMap<Integer, Clients> cache= new HashMap<Integer, Clients>();
}
