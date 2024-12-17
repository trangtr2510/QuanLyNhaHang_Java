package com.raven.Connection;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    // Private constructor to enforce singleton pattern
    public DatabaseConnection() {
        try {
            connectToDatabase(); // Attempt to connect when the instance is created
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối tới cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Singleton pattern to get the instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Connect to the database
    public void connectToDatabase() throws SQLException {
        var server = "DESKTOP-76G9CHD";  // Update your server name
        var user = "sa";
        var pass = "25102004";
        var db = "QLNHang";
        var port = 1433;

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(user);
        ds.setPassword(pass);
        ds.setDatabaseName(db);
        ds.setServerName(server);
        ds.setPortNumber(port);
        ds.setEncrypt(true);  // Enable encryption
        ds.setTrustServerCertificate(true);  // Allow untrusted server certificates

        connection = ds.getConnection();  // Obtain connection
    }

    // Get the connection, if not already connected, attempt to reconnect
    public Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try {
                connectToDatabase();  // Reconnect if the connection is null or closed
            } catch (SQLException e) {
                System.err.println("Lỗi khi kết nối lại tới cơ sở dữ liệu: " + e.getMessage());
            }
        }
        return connection;
    }

    // Check if the connection is closed
    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra kết nối: " + e.getMessage());
            return true;
        }
    }

    // Close the connection
    public static void closeConnection() {
        try {
            if (instance != null && instance.connection != null && !instance.connection.isClosed()) {
                instance.connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }
}
