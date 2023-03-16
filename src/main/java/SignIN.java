import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignIN extends JFrame implements ActionListener {

    JComboBox userTypes;
    JTextField usernametf;
    JPasswordField passwordtf;
    public static int readerID;

    SignIN() {

        this.setTitle("LIBRARY");
        this.setSize(500, 400);

        JLabel welcome = new JLabel("WELCOME TO OUR LIBRARY! ");
        welcome.setBounds(30, 30, 500, 50);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.add(welcome);

        JLabel loginType = new JLabel("WHO ARE YOU? ");
        loginType.setBounds(100, 100, 100, 30);
        this.add(loginType);

        userTypes = new JComboBox();
        userTypes.setBounds(200, 105, 200, 20);
        userTypes.addItem("ADMIN");
        userTypes.addItem("READER");
        this.add(userTypes);

        JLabel username = new JLabel("USERNAME:");
        username.setBounds(100, 160, 100, 30);
        this.add(username);

        usernametf = new JTextField();
        usernametf.setBounds(200, 165, 200, 20);
        this.add(usernametf);

        JLabel password = new JLabel("PASSWORD:");
        password.setBounds(100, 220, 100, 30);
        this.add(password);

        passwordtf = new JPasswordField();
        passwordtf.setBounds(200, 225, 200, 20);
        this.add(passwordtf);

        JButton signin = new JButton("SIGN IN");
        signin.setBounds(120, 300, 100, 30);
        signin.addActionListener(this);
        this.add(signin);

        JButton signup = new JButton("Not registered? SIGN UP");
        signup.setBounds(250, 300, 200, 30);
        signup.addActionListener(this);
        this.add(signup);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("SIGN IN")) {

            boolean isUser = checkUser(usernametf.getText(), passwordtf.getText(), userTypes.getSelectedItem().toString());
            if (isUser) {
                usernametf.setText("");
                passwordtf.setText("");

                JOptionPane.showMessageDialog(null, "Signing in...");
                if (userTypes.getSelectedItem().toString().equals("ADMIN")) {

                    this.setVisible(false);
                    new AdminPanel();
                }
                else if (userTypes.getSelectedItem().toString().equals("READER")) {

                    this.setVisible(false);
                    new ReadersPanel();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid credentials, try again.");
            }
        }
        else if (ae.getActionCommand().equals("Not registered? SIGN UP")) {

            this.setVisible(false);
            new SignUP();

        }
    }
    public boolean checkUser(String username, String password, String userType) {

        Connection con = DbProvider.makeConnection();
        Statement stmt;
        try {

            stmt = con.createStatement();
            ResultSet rs;

            if (userType.equals("READER")) {

                rs = stmt.executeQuery(" select * from readers where name='" + username + "' and password='" + password + "'");
            }
            else {

                rs = stmt.executeQuery(" select * from admin where name='" + username + "' and password='" + password + "'");
            }

            if (rs.next()) {
                readerID = rs.getInt("id");
                return true;

            }

        } catch (SQLException ex) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {

        SignIN start = new SignIN();

    }
}