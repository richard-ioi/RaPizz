package DataAccess;
import java.awt.event.*;
import Graphics.IHM;

public class IHM_ActionListener implements ActionListener {
    public IHM_ActionListener (IHM view){
        //View=view;
    }
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == IHM.boutonOngletMenu) {
            JdbcConnectDB.printTest();
        }
    }
}
