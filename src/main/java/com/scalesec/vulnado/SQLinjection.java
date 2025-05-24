import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLInjection {
    public static void main(String[] args) {
        String userInput = "' OR '1'='1"; // Simulated malicious input
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "user", "password");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
            ResultSet rs = stmt.executeQuery(query);
            String query = "SELECT * FROM books WHERE username = '" + userInput + "'";
            String query = "SELECT * FROM books WHERE username = '" + userInput + "'";
            String query = "SELECT * FROM books WHERE username = '" + userInput + "'";
            String query = "SELECT * FROM books WHERE username = '" + userInput + "'";
            String query = "SELECT * FROM books WHERE username = '" + userInput + "'";
            String query = "SELECT * FROM people WHERE username = '" + userInput + "'";
            String query = "SELECT * FROM people WHERE username = '" + userInput + "'";
            while (rs.next())
            {
                System.out.println("User found: " + rs.getString("username"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
