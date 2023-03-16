import org.junit.jupiter.api.Test;

import java.sql.*;
import static org.junit.Assert.assertEquals;
import java.sql.Connection;
import java.sql.SQLException;


class BooksTest {

    AdminPanel a = new AdminPanel();

    @Test
    void addBookTest() {

        Connection myConnection = DbProvider.makeConnection();

        Book book = new Book();
        book.isbn = "0000";
        book.name = "bookTest";
        book.author = "authorTest";
        book.numOfPages = 0;

        String testBookName = book.getName();
        String testBookAuthor = book.getAuthor();
        String testBookISBN = book.getIsbn();
        int testBookNOP = book.getNumOfPages();

        AddBook add = new AddBook(book);

        add.addBook(book);

        try {
            Statement myStatement = myConnection.createStatement();

            ResultSet myResultSet = myStatement.executeQuery("select count(*) from books where name = '" + testBookName + "' and author = '" + testBookAuthor + "'");
            myResultSet.next();
            int output = myResultSet.getInt("count(*)");
            assertEquals(1, output);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}