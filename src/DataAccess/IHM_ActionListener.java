package DataAccess;
import java.awt.event.*;
import Graphics.IHM;

public class IHM_ActionListener implements ActionListener {
    IHM View;
    public IHM_ActionListener (IHM view){
        View=view;
    }
    public void actionPerformed(ActionEvent e) {

        JdbcConnectDB.execQueryDonneesBrutes(View.queryDonneesBrutes.getText());
    }
}
