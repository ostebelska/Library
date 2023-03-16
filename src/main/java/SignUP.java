import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class SignUP extends JFrame implements ActionListener {

    JTextField usernametf, emailtf;
    JPasswordField passwordtf;

    SignUP() {

        this.setTitle("REGISTRATION");
        this.setSize(500, 500);

        JLabel welcome = new JLabel("LIBRARY'S REGISTRATION SECTION...");
        welcome.setBounds(30, 30, 400, 40);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));

        this.add(welcome);

        JLabel username = new JLabel("USERNAME:");
        username.setBounds(100, 100, 100, 30);
        this.add(username);

        usernametf = new JTextField();
        usernametf.setBounds(200, 105, 200, 20);
        this.add(usernametf);

        JLabel password = new JLabel("PASSWORD:");
        password.setBounds(100, 160, 100, 30);
        this.add(password);

        passwordtf = new JPasswordField();
        passwordtf.setBounds(200, 165, 200, 20);
        this.add(passwordtf);

        JLabel email = new JLabel("EMAIL:");
        email.setBounds(100, 230, 100, 30);
        this.add(email);

        emailtf = new JTextField();
        emailtf.setBounds(200, 235, 200, 20);
        this.add(emailtf);

        JButton register = new JButton("REGISTER");
        register.setBounds(150, 350, 100, 30);
        register.addActionListener(this);
        this.add(register);

        JButton exit = new JButton("EXIT");
        exit.setBounds(280, 350, 100, 30);
        exit.addActionListener(this);
        this.add(exit);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("REGISTER")) {

            this.addReader(usernametf.getText(), passwordtf.getText(), emailtf.getText());

            JOptionPane.showMessageDialog(null, "SUCCESSFULLY REGISTERED");
        }
        else if (ae.getActionCommand().equals("EXIT")) {
            JOptionPane.showMessageDialog(null, "SEE YOU LATER!");
            System.exit(0);

        }
    }

    public void addReader(String name, String password, String email) {
        try {

            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            st.execute("insert into readers(name,password,email) values('" + name + "','" + password + "','" + email + "');");

            this.setVisible(false);
            new SignIN();

        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}