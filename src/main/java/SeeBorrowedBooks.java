import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class SeeBorrowedBooks extends JFrame implements ActionListener{

    SeeBorrowedBooks() {

        ArrayList<Book> books = getBooks();

        System.out.println(books);
        String column[] = {"ISBN", "NAME", "AUTHOR", "PAGES"};

        DefaultTableModel tableModel = new DefaultTableModel(column, 0);

        JTable table = new JTable(tableModel);

        for (int i = 0; i < books.size(); i++) {
            Object[] bookDate = {books.get(i).getIsbn(), books.get(i).getName(), books.get(i).getAuthor(), books.get(i).getNumOfPages()};
            tableModel.addRow(bookDate);
        }
        table.setBounds(30, 40, 700, 400);

        JButton backToDashboard = new JButton("BACK TO MAIN PANEL");
        backToDashboard.setBounds(150, 400, 200, 30);
        backToDashboard.addActionListener(this);
        this.add(backToDashboard);

        JScrollPane sp = new JScrollPane(table);
        this.add(sp);

        this.setTitle("YOUR BORROWED BOOKS");
        this.setSize(500, 500);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }


    public ArrayList<Book> getBooks() {
        ArrayList books = null;
        try {
            books = new ArrayList<>();

            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from borrowed_books where reader_id="+SignIN.readerID);

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

        if(ae.getActionCommand().equals("BACK TO MAIN PANEL")){
            this.setVisible(false);
            new ReadersPanel();
        }
    }
}