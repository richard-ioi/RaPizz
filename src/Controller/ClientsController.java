package Controller;

import Dao.ClientsDAO;
import Domain.Clients;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ClientsController {
    private ClientsDAO clientsDAO;
/*
    public  List<Clients> getAllClients(){
        List<Clients> clientsList = clientsDAO.find("");
        System.out.print(clientsList.toString());
        return clientsList;
    }*/

}
