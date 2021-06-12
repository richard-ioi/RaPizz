package DataAccess;
import java.awt.event.*;

public class IHM_Listener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        JdbcConnectDB.printTest();
    }
}
