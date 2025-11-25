package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/creamCafe";
    private static final String username = "root";
    private static final String password = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connected");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());

        }
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

}
