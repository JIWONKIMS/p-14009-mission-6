package com.back.simpleDb;

import com.back.domain.WiseSaying;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class SimpleDb {
    private final String URL, USERNAME, PASSWORD, DB_NAME;

    public Connection getConnection() {
        String connectionURL = "jdbc:mysql://" + URL + "/" + DB_NAME;
        Connection con = null;
        try {
            con = DriverManager.getConnection(connectionURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return con;
    }
}
