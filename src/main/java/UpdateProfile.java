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

public class UpdateProfile extends JFrame implements ActionListener {

    JTextField nametf, emailtf;
    JPasswordField passwordtf;

    UpdateProfile(Reader reader) {

        this.setTitle("SEE/UPDATE PROFILE PANEL");
        this.setSize(500, 500);

        JLabel welcome = new JLabel("UPDATING PROFILE...");
        welcome.setBounds(30, 30, 400, 40);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.add(welcome);

        JLabel name = new JLabel("Username:");
        name.setBounds(100, 100, 100, 30);
        this.add(name);

        nametf = new JTextField(reader.getName());
        nametf.setBounds(200, 105, 200, 20);
        this.add(nametf);

        JLabel password = new JLabel("Password:");
        password.setBounds(100, 160, 100, 30);
        this.add(password);

        passwordtf = new JPasswordField(reader.getPassword());
        passwordtf.setBounds(200, 165, 200, 20);
        this.add(passwordtf);

        JLabel email = new JLabel("Email:");
        email.setBounds(100, 230, 100, 30);
        this.add(email);

        emailtf = new JTextField(String.valueOf(reader.getEmail()));
        emailtf.setBounds(200, 235, 200, 20);
        this.add(emailtf);

        JButton update = new JButton("UPDATE");
        update.setBounds(100, 350, 100, 30);
        update.addActionListener(this);
        this.add(update);

        JButton back = new JButton("BACK TO MAIN PANEL");
        back.setBounds(230, 350, 200, 30);
        back.addActionListener(this);
        this.add(back);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("UPDATE")) {

            Reader b = new Reader();

            b.setName(nametf.getText());
            b.setEmail(emailtf.getText());
            b.setPassword(passwordtf.getText());

            updateReader(b);

        }
        else if (ae.getActionCommand().equals("BACK TO MAIN PANEL")) {

            this.setVisible(false);
            new ReadersPanel();
        }
    }

    public void updateReader(Reader r) {
        try {
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            st.execute("update  readers set name='" + r.getName() + "', email='" + r.getEmail() + "',password='" + r.getPassword() + "' where id='" + SignIN.readerID + "';");
            JOptionPane.showMessageDialog(null, "Profile updated successfully");

        } catch (SQLException ex) {
            Logger.getLogger(UpdateProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}