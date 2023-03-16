import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AdminPanel extends JFrame implements ActionListener {

    AdminPanel() {

        this.setTitle("ADMIN PANEL");
        this.setSize(520, 550);

        JLabel welcome = new JLabel("CHOOSE SUITABLE OPTION: ");
        welcome.setBounds(100, 50, 300, 40);
        welcome.setFont(new Font("Times New Roman", Font.BOLD, 20));
        this.add(welcome);

        JButton viewBooks = new JButton("View all books");
        viewBooks.setBounds(150, 150, 200, 30);
        viewBooks.setForeground(Color.blue);
        viewBooks.setBackground(Color.white);
        viewBooks.addActionListener(this);
        this.add(viewBooks);

        JButton addBook = new JButton("Add new book");
        addBook.setBounds(150, 200, 200, 30);
        addBook.setForeground(Color.blue);
        addBook.setBackground(Color.white);
        addBook.addActionListener(this);
        this.add(addBook);

        JButton removeBook = new JButton("Remove a book");
        removeBook.setBounds(150, 250, 200, 30);
        removeBook.setForeground(Color.blue);
        removeBook.setBackground(Color.white);
        removeBook.addActionListener(this);
        this.add(removeBook);

        JButton updateBook = new JButton("Update a book");
        updateBook.setBounds(150, 300, 200, 30);
        updateBook.setForeground(Color.blue);
        updateBook.setBackground(Color.white);
        updateBook.addActionListener(this);
        this.add(updateBook);

        JButton logout = new JButton("LOG OUT");
        logout.setBounds(150, 400, 200, 30);
        logout.setBackground(Color.red);
        logout.setForeground(Color.white);
        logout.addActionListener(this);
        this.add(logout);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("Add new book")) {
                this.setVisible(false);
                new AddBook(null);
        }

        if (ae.getActionCommand().equals("View all books")) {
            this.setVisible(false);
            new ShowAllBooks();
        }

        if (ae.getActionCommand().equals("Update a book")){
            String m = JOptionPane.showInputDialog("Enter ISBN of book you would like to update: ");
            Book a = getBook(m);

            if(m == null || (m != null && ("".equals(m)))) {
                JOptionPane.showMessageDialog(null, "Cancelled.");
            }

            else if (a != null) {
                new AddBook(a);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Book with ISBN: " + m + " couldn't be found.");
            }
        }

        else if (ae.getActionCommand().equals("Remove a book")) {
            String m = JOptionPane.showInputDialog("Enter book ISBN you want to delete?");

            boolean bookDeleted = deleteBook(m);

            if(m == null || (m != null && ("".equals(m)))) {
                JOptionPane.showMessageDialog(null, "Cancelled.");
            }

            else if (bookDeleted) {
                JOptionPane.showMessageDialog(null, "Book with ISBN " + m + " deleted successfully.");
            }else {
                JOptionPane.showMessageDialog(null, "Some problem occured while deleting book, try again.");

            }

        }
        else if (ae.getActionCommand().equals("LOG OUT")) {
            this.setVisible(false);
            new SignIN();
        }
    }

    public Book getBook(String isbn) {
        try {

            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from books where isbn='" + isbn + "'");

            while (rs.next()) {

                Book a = new Book();

                a.setIsbn(rs.getString("isbn"));
                a.setName(rs.getString("name"));
                a.setAuthor(rs.getString("author"));
                a.setNumOfPages(rs.getInt("pages"));

                return a;
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    public boolean deleteBook(String isbn) {
        try {

            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            st.execute("delete from books where isbn='" + isbn + "'");
        } catch (SQLException ex) {
            return false;
        }

        return true;
    }
}