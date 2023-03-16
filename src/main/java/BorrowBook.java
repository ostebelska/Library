import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class BorrowBook extends JFrame implements ActionListener {

    JTextField isbntf;

    JSON json = new JSON();

    BorrowBook() {

        ArrayList<Book> books = getBooks();

        String column[] = {"ISBN", "NAME", "AUTHOR", "PAGES"};

        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        JTable table = new JTable(tableModel);

        for (int i = 0; i < books.size(); i++) {

            Object[] bookDate = {books.get(i).getIsbn(), books.get(i).getName(), books.get(i).getAuthor(), books.get(i).getNumOfPages()};

            tableModel.addRow(bookDate);
        }
        table.setBounds(30, 40, 800, 500);

        JLabel isbn = new JLabel("Enter ISBN of book you want to borrow:");
        isbn.setBounds(20, 350, 250, 20);
        this.add(isbn);

        isbntf = new JTextField();
        isbntf.setBounds(20, 370, 240, 20);
        this.add(isbntf);

        JButton borrow = new JButton("BORROW");
        borrow.setBounds(60, 420, 100, 30);
        borrow.addActionListener(this);
        this.add(borrow);

        JButton backToDashboard = new JButton("BACK TO MAIN PANEL");
        backToDashboard.setBounds(260, 420, 200, 30);
        backToDashboard.addActionListener(this);
        this.add(backToDashboard);

        JScrollPane sp = new JScrollPane(table);
        this.add(sp);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("BORROW BOOK PANEL");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        BorrowBook b = new BorrowBook();
    }

    public ArrayList<Book> getBooks() {
        ArrayList books = null;
        try {
            books = new ArrayList<>();

            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from books");

            while (rs.next()) {

                Book a = new Book();
                a.setIsbn(rs.getString("isbn"));
                a.setName(rs.getString("name"));
                a.setAuthor(rs.getString("author"));
                a.setNumOfPages(rs.getInt("pages"));

                books.add(a);

            }
        } catch (SQLException ex) {
            return books;
        }
        return books;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("BACK TO MAIN PANEL")) {
            this.setVisible(false);
            new ReadersPanel();
        }
        else if (ae.getActionCommand().equals("BORROW")) {

            if (isbntf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter isbn id: ");
            }
            else {
                boolean bookBorrowed = addBorrowedBook(isbntf.getText());

                if (bookBorrowed) json.saveToJSON1(isbntf.getText());

                if (bookBorrowed) {

                    JOptionPane.showMessageDialog(null, "Book borrowed successfully.");

                    this.setVisible(false);
                    new BorrowBook();

                }
                else {
                    JOptionPane.showMessageDialog(null, "Some problem occurred, while borrowing book, try again.");

                }
            }
        }
    }


    private boolean addBorrowedBook(String isbn) {
        try {
            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from books where isbn = '" + isbn + "'");
            if (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("author");
                int pages = rs.getInt("pages");

                Statement st2 = con.createStatement();
                st2.execute("insert into borrowed_books values('" + isbn + "','" + name + "','" +  author  + "','" + pages + "'," + SignIN.readerID + ")");

                Statement st3 = con.createStatement();
                st3.execute("delete from books where isbn = '" + isbn + "'");

                return true;
            }
        } catch (SQLException ex) {

            Logger.getLogger(BorrowBook.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }
}