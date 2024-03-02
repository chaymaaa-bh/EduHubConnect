package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyDatabase {

    private final String URL = "jdbc:mysql://localhost:3306/edu";
    private final String USER = "root";
    private final String PASS = "";
    private Connection connection;

    private static MyDatabase instance;

    private MyDatabase(){
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyDatabase getInstance() {
        if(instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/edu", "root", "");
            return connect;
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }
}
