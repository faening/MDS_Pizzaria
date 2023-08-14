package com.faening.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class H2DatabaseConnection {
    private static final String DB_URL = "jdbc:h2:~/pizzeria_db";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
