import org.testng.annotations.Test;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbProviderTest {


    public DbProviderTest() {
    }

    @Test
    public void testGetConnection(){

        Connection con = null;

       try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        } catch (ClassNotFoundException ex) {
           // ex.printStackTrace();
            System.out.println(ex);

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
            System.out.println(e);
        }
    }

    @Test
    public void SignINTest() {

        Reader r = new Reader();
        String User = "oliwia";
        String Pass = "oliwia";

        Connection con = DbProvider.makeConnection();
        Statement stmt;

        try {

            stmt = con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery(" select * from readers where name='" + User + "' and password='" + Pass + "'");

            r.name=rs.getString("name");
            r.email=rs.getString("email");
            System.out.println(r.name + " " +r.email);

            con.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void SignUPTest() {

        Reader reader = new Reader();
        SignUP signUP = new SignUP();

        reader.name="testName";
        reader.email="testEmail";
        reader.password = "testPass";

        Connection con = DbProvider.makeConnection();

        String testUserAccount = reader.getName();
        String testUserPassword = reader.getPassword();
        String testUserEmail = reader.getEmail();

        signUP.addReader(testUserAccount, testUserPassword, testUserEmail);

        try {
            Statement myStatement = con.createStatement();

            ResultSet myResultSet = myStatement.executeQuery("select count(*) from readers where name = '"+testUserAccount+"'");

            myResultSet.next();
            int output = myResultSet.getInt("count(*)");
            assertEquals(1, output);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}



