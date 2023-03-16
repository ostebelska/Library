import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbProvider {

    private DbProvider() {}
    static Connection con = null;

    public static Connection makeConnection(){
        if (con == null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("Driver Not Loading");

            } catch (InstantiationException ex) {
                Logger.getLogger(DbProvider.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IllegalAccessException ex) {
                Logger.getLogger(DbProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Successfully connected");

            try {
                String url = "jdbc:mysql://localhost:3306/library";
                String uname = "root";
                String pwd = "X6kHp466]N@mEXB";
                con = DriverManager.getConnection(url, uname, pwd);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}
