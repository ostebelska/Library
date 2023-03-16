import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class ReadersPanel extends JFrame implements ActionListener{

    JSON json = new JSON();

    ReadersPanel(){

        this.setTitle("READER PANEL");
        this.setSize(520,550);

        JLabel welcome = new JLabel("CHOOSE SUITABLE OPTION: ");
        welcome.setBounds(100,50,300,40);
        welcome.setFont(new Font("Times New Roman",Font.BOLD,20));
        this.add(welcome);

        JButton borrowBook = new JButton("Borrow a book");
        borrowBook.setBounds(150, 150, 200, 30);
        borrowBook.setForeground(Color.blue);
        borrowBook.setBackground(Color.white);
        borrowBook.addActionListener(this);
        this.add(borrowBook);


        JButton seeBorrowedBooks = new JButton("See borrowed books");
        seeBorrowedBooks.setBounds(150, 200, 200, 30);
        seeBorrowedBooks.setForeground(Color.blue);
        seeBorrowedBooks.setBackground(Color.white);
        seeBorrowedBooks.addActionListener(this);

        this.add(seeBorrowedBooks);

        JButton returnBook = new JButton("Return a book");
        returnBook.setBounds(150, 250, 200, 30);
        returnBook.setForeground(Color.blue);
        returnBook.setBackground(Color.white);
        returnBook.addActionListener(this);

        this.add(returnBook);

        JButton seeProfile = new JButton("See/Update profile");
        seeProfile.setBounds(150, 300, 200, 30);
        seeProfile.setBackground(Color.white);
        seeProfile.setForeground(Color.blue);
        seeProfile.addActionListener(this);

        this.add(seeProfile);

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

        if (ae.getActionCommand().equals("Borrow a book")) {
            this.setVisible(false);
            new BorrowBook();

        }
        else if (ae.getActionCommand().equals("See borrowed books")) {
            this.setVisible(false);
            new SeeBorrowedBooks();
        }
        else if(ae.getActionCommand().equals("Return a book")){

            String m = JOptionPane.showInputDialog("Enter book ISBN you want to return?");
            boolean bookReturned = returnBook(m);

            if(m == null || (m != null && ("".equals(m)))) {
                JOptionPane.showMessageDialog(null, "Cancelled.");
            }

            else if(bookReturned){

                JOptionPane.showMessageDialog(null, "Book with ISBN "+m+" returned successfully.");
                json.saveToJSON2(m);

            } else{
                JOptionPane.showMessageDialog(null, "Some problem has occurred while returning book, try again.");

            }
        }

        else if(ae.getActionCommand().equals("See/Update profile")){
            try {

                Reader r = new Reader();

                Connection con = DbProvider.makeConnection();
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("select * from readers where id = "+SignIN.readerID);

                if(rs.next()){
                    r.setEmail(rs.getString("email"));
                    r.setName(rs.getString("name"));
                    r.setPassword(rs.getString("password"));

                    this.setVisible(false);
                    new UpdateProfile(r);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReadersPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        else if(ae.getActionCommand().equals("LOG OUT")){
            this.setVisible(false);
            new SignIN();
        }
    }

    private boolean returnBook(String isbn) {
        try {

            Connection con = DbProvider.makeConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from borrowed_books where isbn='"+isbn+"' and reader_id="+SignIN.readerID+";");

            if(rs.next()){
                String name = rs.getString("name");
                String author = rs.getString("author");
                int pages = rs.getInt("pages");
                Statement st2 = con.createStatement();

                st2.execute("insert into books values('" + isbn + "','" + name + "','" + author + "'," + pages+" )");

            }
            Statement st3 = con.createStatement();
            st3.execute("delete from borrowed_books where isbn='"+isbn+"' and reader_id="+SignIN.readerID);

        } catch (SQLException ex) {
            return false;
        }

        return true;
    }
}