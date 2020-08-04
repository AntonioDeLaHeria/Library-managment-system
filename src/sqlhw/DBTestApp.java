package sqlhw;

import java.sql.*;
import java.text.NumberFormat;
import java.util.Scanner;

public class DBTestApp {
	
    public static void main(String args[]) {

        // Load the database driver
        // NOTE: This block is necessary for Oracle 10g (JDBC 3.0) or earlier,
        // but not for Oracle 11g (JDBC 4.0) or later
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        // define common JDBC objects
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            // Connect to the database
            String dbUrl = "jdbc:mysql://34.73.93.117:3306/";
            String username = "root";
            String password = "Anto2020!";
            connection = DriverManager.getConnection(
                    dbUrl, username, password);

            // Execute a SELECT statement
            statement = connection.createStatement();
            String query = "SELECT * FROM BTE423Final.Book;";
            rs = statement.executeQuery(query);
            
            // Display the results of a SELECT statement
            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String Author = rs.getString("Author");
                String Title = rs.getString("Title");
                String item = rs.getString("item");
                String pending = rs.getString("pending");
              

                NumberFormat currency = NumberFormat.getCurrencyInstance();
               // String invoiceTotalString = currency.format(ISBN);

                System.out.println(
                      "ISBN" +  ISBN + "\n"
                      + "Author" + Author + "\n"
                      + "Title" + Title + "\n"
                      + "item" + item + "\n"
                      + "item" + item + "\n"
                      +"pending" + pending + "\n"
                     
                    );
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
