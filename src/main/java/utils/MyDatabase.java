package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyDatabase {

<<<<<<< HEAD
    private final String URL = "jdbc:mysql://localhost:3306/EduHubConnect";
    private final String USER = "root";
    private final String PASS = "root";
=======
    private final String URL = "jdbc:mysql://localhost:3306/3a2";
    private final String USER = "root";
    private final String PASS = "";
>>>>>>> origin/chayma
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
<<<<<<< HEAD
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/EduHubConnect", "root", "root");
=======
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/3a2", "root", "");
>>>>>>> origin/chayma
            return connect;
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }
}
