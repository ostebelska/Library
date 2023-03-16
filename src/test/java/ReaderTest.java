import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;


public class ReaderTest {

    private Reader reader1;
    private Reader reader2;

    @Before
    public void setUp() {
        reader1 = new Reader("oliwia1", "12345");
        reader2 = new Reader();
        reader2.setName("random1");
        reader2.setPassword("54321");
    }

    @Test
    public void getUsernameTest() {
        assertEquals("oliwia1", reader1.getName());
    }

    @Test
    public void setUsernameTest() {assertEquals("random1", reader2.getName());}

    @Test
    public void getPasswordTest() {
        assertEquals("12345", reader1.getPassword());
    }

    @Test
    public void setPasswordTest() {
        assertEquals("54321", reader2.getPassword());
    }

}