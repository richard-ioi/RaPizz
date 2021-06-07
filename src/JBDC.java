import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JBDC {
    public static void main(String[] args) throws SQLException {
        String url = "jbdc:mysql:/RAPIZZ";
        String uname = "Admin";
        String password = "RichardEtEmilyMeritentUn20/20";

        try {
            Class c = Class.forName("com.mysql.jbdc.Driver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            Connection con = DriverManager.getConnection(url,uname,password);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
