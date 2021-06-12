package DataAccess;
import java.awt.event.*;
import Graphics.IHM;

public class IHM_ActionListener implements ActionListener {
    IHM View;
    public IHM_ActionListener (IHM view){
        View=view;
    }
    public void actionPerformed(ActionEvent e) {
        // Créer une fonction execQuerryDonneesBrutes (la requête est entrée en paramètre
        //sous forme de STRING) puis utiliser IHM.createDonneesBrutesJTable pour
        // retourner une JTable avec les résultats de la requête

        //JdbcConnectDB.execQuerryDonneesBrutes(View.querryDonneesBrutes.getText());
    }
}
