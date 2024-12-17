
package com.raven.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    public DatabaseConnection() {
         try {
            connectToDatabase(); // Thực hiện kết nối khi tạo instance
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối tới cơ sở dữ liệu: " + e.getMessage());
        }
    }
    //Thực hiện kết nối tới Database
    public void connectToDatabase() throws SQLException {
            String url = "jdbc:oracle:thin:@//localhost:1521/orcl21";
            String uname = "System";
            String upass = "25102004";
            connection = DriverManager.getConnection(url, uname, upass);
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
