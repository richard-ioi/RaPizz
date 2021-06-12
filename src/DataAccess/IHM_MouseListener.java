package DataAccess;
import java.awt.event.*;

public class IHM_MouseListener implements MouseListener {
    String Type;
    int ID;

    public IHM_MouseListener (String pType, int pID){
        Type=pType;
        ID=pID;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // the user clicks on the label
        if(Type=="CLIENT"){
            //JbdcConnectDB.selectCLient(ID);
        }
        if(Type=="PIZZA"){
            //JbdcConnectDB.selectPizza(ID);
        }
        if(Type=="LIVREUR"){
            //JbdcConnectDB.selectLivreur(ID);
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
