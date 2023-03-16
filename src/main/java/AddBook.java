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
import javax.swing.JTextField;

public class AddBook extends JFrame implements ActionListener {

    JTextField isbntf, nametf, numOfPagestf, authortf;

    AddBook(Book book) {

        if (book == null) {

            this.setTitle("ADD BOOK PANEL");
            this.setSize(500, 500);

            JLabel welcome = new JLabel("ADDING NEW BOOK...");
            welcome.setBounds(30, 30, 400, 40);
            welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
            this.add(welcome);

            JLabel isbn = new JLabel("ISBN:");
            isbn.setBounds(90, 100, 100, 30);
            this.add(isbn);

            isbntf = new JTextField();
            isbntf.setBounds(200, 105, 200, 20);
            this.add(isbntf);

            JLabel name = new JLabel("Book Name:");
            name.setBounds(90, 160, 100, 30);
            this.add(name);

            nametf = new JTextField();
            nametf.setBounds(200, 165, 200, 20);
            this.add(nametf);

            JLabel author = new JLabel("Author:");
            author.setBounds(90, 230, 100, 30);
            this.add(author);

            authortf = new JTextField();
            authortf.setBounds(200, 235, 200, 20);
            this.add(authortf);

            JLabel numOfPages = new JLabel("Number of pages:");
            numOfPages.setBounds(90, 300, 100, 30);
            this.add(numOfPages);

            numOfPagestf = new JTextField();
            numOfPagestf.setBounds(200, 305, 200, 20);
            this.add(numOfPagestf);

            JButton add = new JButton("ADD");
            add.setBounds(70, 400, 100, 30);
            add.addActionListener(this);
            this.add(add);

        }
        else {

            this.setTitle("UPDATE BOOK PANEL");
            this.setSize(500, 500);

            JLabel welcome = new JLabel("UPDATING BOOK...");
            welcome.setBounds(30, 30, 400, 40);
            welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
            this.add(welcome);

            JLabel isbn = new JLabel("ISBN:");
            isbn.setBounds(90, 100, 100, 30);
            this.add(isbn);

            isbntf = new JTextField(book.getIsbn());
            isbntf.setBounds(200, 105, 200, 20);
            isbntf.setEnabled(false);
            this.add(isbntf);

            JLabel name = new JLabel("Book Name:");
            name.setBounds(90, 160, 100, 30);
            this.add(name);

            nametf = new JTextField(book.getName());
            nametf.setBounds(200, 165, 200, 20);
            this.add(nametf);

            JLabel author = new JLabel("Author: ");
            author.setBounds(90, 230, 100, 30);
            this.add(author);

            authortf = new JTextField(book.getAuthor());
            authortf.setBounds(200, 235, 200, 20);
            this.add(authortf);

            JLabel numOfPages = new JLabel("Number of pages:");
            numOfPages.setBounds(90, 300, 100, 30);
            this.add(numOfPages);

            numOfPagestf = new JTextField(String.valueOf(book.getNumOfPages()));
            numOfPagestf.setBounds(200, 305, 200, 20);
            this.add(numOfPagestf);

            JButton update = new JButton("UPDATE");
            update.setBounds(70, 400, 100, 30);
            update.addActionListener(this);
            this.add(update);

        }

        JButton exit = new JButton("BACK TO MAIN PANEL");
        exit.setBounds(220, 400, 200, 30);
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

        if (ae.getActionCommand().equals("ADD")) {

            Book b = new Book();

            b.setIsbn(isbntf.getText());
            b.setName(nametf.getText());
            b.setAuthor(authortf.getText());
            b.setNumOfPages(Integer.parseInt(numOfPagestf.getText()));

            addBook(b);

        }
        else if (ae.getActionCommand().equals("UPDATE")) {
            Book b = new Book();

            b.setIsbn(isbntf.getText());
            b.setName(nametf.getText());
            b.setAuthor(authortf.getText());
            b.setNumOfPages(Integer.parseInt(numOfPagestf.getText()));

            updateBook(b);

        }
        else if (ae.getActionCommand().equals("BACK TO MAIN PANEL")) {
            this.setVisible(false);
            new AdminPanel();
        }
    }


    public void addBook(Book b) {
        try {
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            st.execute("insert into books values('" + b.getIsbn() + "','" + b.getName() + "','" + b.getAuthor() + "'," + b.getNumOfPages() + ");");

            JOptionPane.showMessageDialog(null, "Book added successfully");

            isbntf.setText("");
            nametf.setText("");
            authortf.setText("");
            numOfPagestf.setText("");

        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateBook(Book b) {
        try {

            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            st.execute("update  books set name='" + b.getName() + "', author='" + b.getAuthor() + "',pages=" + b.getNumOfPages() + " where isbn='" + b.getIsbn() + "';");

            JOptionPane.showMessageDialog(null, "Book updated successfully");

            isbntf.setText("");
            nametf.setText("");
            authortf.setText("");
            numOfPagestf.setText("");

        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
