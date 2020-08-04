package sqlhw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Scanner;

public class menu {
	public static void main(String[] args) {

		try {
			Class.forName("com.oracle.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}

		// define common JDBC objects
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			// Connect to the database
			String dbUrl = "jdbc:oracle://1521/";
			String username = "root";
			String password = "Anto2020!";
			connection = DriverManager.getConnection(dbUrl, username, password);

			// Execute a SELECT statement
			statement = connection.createStatement();

			System.out.println("Select from the following options");
			System.out.println("1List the numer of copies of a particular library item");
			System.out.println("2-list the details of the patrons who have at least an overdue library item today");
			System.out.println("3-Identify the total fines owed by a patron");
			System.out.println("4-List the dailts of the payment made by a patron");
			System.out.println("5-List the copies of library items that are grossly overdue");
			System.out.println("6-List the details of the current pending request in the system");
			System.out.println("7-Identify the toal fines revenue to the lirary between April 1st 2019 and October 1st 2019");
			System.out.println("8-List the details of the checkout periods and hte number of renewals fro all the items");
			System.out.println("9-List the toal number and the details of library items that are checked out");
			System.out.println("10-Insert a new pending request made by a patron");
			System.out.println("11- quite program");

			Scanner read = new Scanner(System.in);
			int option;
			String query;

			while (true) {
				option = read.nextInt();
				switch (option) {
				case 1:
					query = "SELECT * FROM BTE423Final.Book;";
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
		            break;
				case 2:
					query = "SELECT customer_name,c.customerID,address\n"
							+ "FROM BTE423Final.Customer c, BTE423Final.Issue_Status s\n"
							+ "WHERE returned > current_date() AND c.customerID = s.customerID;";

					rs = statement.executeQuery(query);

					while (rs.next()) {
						String customer_name = rs.getString("customer_name");
						int customerID = rs.getInt("customerID");
						String address = rs.getString("address");

						NumberFormat currency = NumberFormat.getCurrencyInstance();
						// String invoiceTotalString = currency.format(customer_name);

						System.out.println("customer_name " + customer_name + "\n" + "customerID" + customerID + "\n"
								+ "address" + address + "\n");
					}
					break;
				case 3:
					query = "SELECT customerID,SUM(fine + numDays) AS TotalFine\n" + 
							"FROM BTE423Final.Issue_Status s , BTE423Final.maintenance m\n" + 
							"WHERE s.ISBN = m.ISBN and fine > 0\n" + 
							"GROUP BY customerID;";
					
					rs = statement.executeQuery(query);

					// Display the results of a SELECT statement
					System.out.println("list details of the patrons who have at least one overdue library Item");
					while (rs.next()) {
						int customerID = rs.getInt("customerID");
						int totalFine = rs.getInt("TotalFine");

						NumberFormat currency = NumberFormat.getCurrencyInstance();
						// String invoiceTotalString = currency.format(customer_name);

						System.out.println("customerID" + customerID + "\n" + "totalFine" + totalFine + "\n");
					}
					break;
				case 4:
					query = "SELECT c.customerID, c.customer_name, m.fine,m.damage,m.lost,m.payment\n" + 
		            		"FROM BTE423Final.Customer c, BTE423Final.Issue_Status s, BTE423Final.maintenance m\n" + 
		            		"WHERE c.customerID = s.customerID AND s.ISBN = m.ISBN\n" + 
		            		"AND payment > 0;";
					 rs = statement.executeQuery(query);
					
					 while (rs.next()) {
			                int customerID = rs.getInt("customerID");
			                String customer_name = rs.getString("customer_name");
			                int fine = rs.getInt("fine");
			                String damage = rs.getString("damage");
			                String lost = rs.getString("lost");
			                int payment = rs.getInt("payment"); 
			                
			              

			                NumberFormat currency = NumberFormat.getCurrencyInstance();
			               // String invoiceTotalString = currency.format(customer_name);

			                System.out.println(
			                      "customerID" +  customerID + "\n"
			                     + "customer_name" + customer_name + "\n"
			                     + "fine" + fine + "\n"
			                     + "damage" + damage + "\n"
			                     + "lost" + lost + "\n"
			                     + "payment" + payment + "\n"
			                    );
			            }
					 break;
				case 5:
					query = "SELECT customer_name,c.customerID,b.ISBN,b.Title\n" + 
		            		"FROM BTE423Final.Customer c, BTE423Final.Issue_Status s,BTE423Final.Book b\n" + 
		            		"WHERE returned >= checkin AND c.customerID = s.customerID AND s.ISBN = b.ISBN;";
		            rs = statement.executeQuery(query);

		            // Display the results of a SELECT statement
		            while (rs.next()) {
		                int customerID = rs.getInt("customerID");
		                String customer_name = rs.getString("customer_name");
		                String ISBN = rs.getString("ISBN");
		                String title = rs.getString("Title");
		                
		              

		                NumberFormat currency = NumberFormat.getCurrencyInstance();
		               // String invoiceTotalString = currency.format(customer_name);

		                System.out.println(
		                      "customerID" +  customerID + "\n"
		                     + "customer_name" + customer_name + "\n"
		                     + "ISBN" + ISBN + "\n"
		                     + "Title" + title + "\n"
		                    );
		            }
		            break;
				case 6:
					query = "SELECT ISBN,Title,Author,item, pending\n" + 
		            		"FROM BTE423Final.Book\n" + 
		            		"WHERE pending = 'yes';";
		            rs = statement.executeQuery(query);

		            // Display the results of a SELECT statement
		            while (rs.next()) {
		                String Author = rs.getString("Author");
		                String ISBN = rs.getString("ISBN");
		                String title = rs.getString("Title");
		                String item = rs.getString("item");
		                String pending = rs.getString("pending");
		              

		                NumberFormat currency = NumberFormat.getCurrencyInstance();
		               // String invoiceTotalString = currency.format(ISBN);

		                System.out.println(
		                      "ISBN" +  ISBN + "\n"
		                     + "Title" + title + "\n"
		                     + "Author" + Author + "\n"
		                     + "item" + item + "\n"
		                     + "pending" + pending + "\n"
		                    );
		            }
		            break;
				case 7:
					query = "SELECT SUM(fine) AS revenue \n" + 
		            		"FROM BTE423Final.maintenance, BTE423Final.Issue_Status \n" + 
		            		"WHERE 2019-4-1 < returned AND returned > 2019-10-1;";
		            rs = statement.executeQuery(query);

		            // Display the results of a SELECT statement
		            while (rs.next()) {
		                int sum = rs.getInt("revenue");
		              

		                NumberFormat currency = NumberFormat.getCurrencyInstance();
		               // String invoiceTotalString = currency.format(ISBN);

		                System.out.println(
		                      "SUM(fine)" +  sum + "\n"
		                     
		                    );
		            }
		            break;
				case 8:
					query = "SELECT COUNT(item) AS myCount,customerID,s.ISBN,Author,s.Title,s.checkout,renew\n" + 
		            		"FROM BTE423Final.Book b, BTE423Final.Issue_Status s\n" + 
		            		"WHERE renew = 'Yes' AND b.ISBN = s.ISBN\n" + 
		            		"GROUP BY ISBN;";
		            rs = statement.executeQuery(query);

		            // Display the results of a SELECT statement
		            while (rs.next()) {
		                int myCount = rs.getInt("myCount");
		                int customerID = rs.getInt("customerID");
		                String ISBN = rs.getString("ISBN");
		                String Author = rs.getString("Author");
		                String Title = rs.getString("Title");
		                String checkout = rs.getString("checkout");
		                String renew = rs.getString("renew");
		              

		                NumberFormat currency = NumberFormat.getCurrencyInstance();
		               // String invoiceTotalString = currency.format(ISBN);

		                System.out.println(
		                      "myCount" +  myCount + "\n"
		                      + "customerID" + customerID + "\n"
		                      + "ISBN" + ISBN + "\n"
		                      + "Author" + Author + "\n"
		                      + "Title" + Title + "\n"
		                      + "checkout" + checkout + "\n"
		                      + "renew" + renew + "\n"
		                     
		                    );
		            }
		            break;
				case 9:
					 query = "SELECT  b.ISBN,s.Title,checkout,COUNT(renew) AS numRenew,item\n" + 
			            		"FROM BTE423Final.Issue_Status s,BTE423Final.Book b\n" + 
			            		"WHERE renew = 'yes'\n" + 
			            		"GROUP BY b.ISBN,s.ISBN;";
			            rs = statement.executeQuery(query);

			            // Display the results of a SELECT statement
			            while (rs.next()) {
			                String ISBN = rs.getString("ISBN");
			                String Title = rs.getString("Title");
			                String checkout = rs.getString("checkout");
			                int renew = rs.getInt("numrenew");
			                String item = rs.getString("item");
			              

			                NumberFormat currency = NumberFormat.getCurrencyInstance();
			               // String invoiceTotalString = currency.format(ISBN);

			                System.out.println(
			                      "ISBN" +  ISBN + "\n"
			                      + "Title" + Title + "\n"
			                      + "checkout" + checkout + "\n"
			                      + "numrenew" + renew + "\n"
			                      +"item" + item + "\n"
			                     
			                    );
			            }
			            break;
				case 10:
					query = "INSERT INTO BTE423Final.Book(ISBN,Author,Title,item,pending)\n" + 
		            		"VALUES('#620','Christopher Nolan','Batman Begins','Movie','yes');";
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
		            break;
				case 11:
					System.out.println(" You have exited the system");
					break;
				}
				
				if (option == 11)
					break;

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
