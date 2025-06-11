package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBContext {
    private static Properties dbProperties = new Properties();
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;
    private static String dbDriver;

    static {
    try (InputStream input = DBContext.class.getClassLoader().getResourceAsStream("util/db.properties")) {
        if (input == null) {
            System.err.println("!!! FATAL ERROR: util/db.properties NOT FOUND ON CLASSPATH !!!");
        } else {
            dbProperties.load(input);
            dbDriver = dbProperties.getProperty("db.driver");
            dbUrl = dbProperties.getProperty("db.url");
            dbUser = dbProperties.getProperty("db.username");
            dbPassword = dbProperties.getProperty("db.password"); 

            System.out.println("DBContext: Loaded driver: " + dbDriver);
            System.out.println("DBContext: Loaded URL: " + dbUrl);
            System.out.println("DBContext: Loaded user: " + dbUser);

            Class.forName(dbDriver); // Load driver
            System.out.println("DBContext: Driver loaded successfully.");
        }
    } catch (Exception e) {
        System.err.println("DBContext: EXCEPTION DURING STATIC INITIALIZATION:");
        e.printStackTrace();
    }
}


    public static Connection getConnection() throws SQLException {
        if (dbUrl == null || dbUser == null) { 
            throw new SQLException("Database configuration not loaded properly from db.properties.");
        }
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // Main method for testing connection 
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("Database connection test successful!");
                conn.close();
            } else {
                System.out.println("Database connection test failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}