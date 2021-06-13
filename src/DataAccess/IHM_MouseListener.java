package DataAccess;


import java.awt.event.*;

public class IHM_MouseListener implements MouseListener {
    String Type;
    int ID;
    //private JdbcConnectDB JbdcConnectDB;

    public IHM_MouseListener (String pType, int pID){
        Type=pType;
        ID=pID;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(Type=="CLIENT"){
            // Créer une fonction selectClient (ID fourni en paramètre)
            // puis utiliser IHM.createCommandesJTable pour créer le résultat
            // dans l'IHM

            JdbcConnectDB.getClientById(3);
        }
        if(Type=="PIZZA"){
            //JbdcConnectDB.selectPizza(ID);
        }
        if(Type=="LIVREUR"){
            JdbcConnectDB.getLivreurById(1);
        }
        System.out.println("TYPE="+Type+" ID="+ID);

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
