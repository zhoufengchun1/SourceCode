package com.example.fangdou;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    public static Connection connection;
    public static Statement statement;

    public static Connection connSql() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://47.100.195.173:3306/UserInfo", "root", "admin");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return
                connection;
    }

    public static Statement getStatement() {
        new Thread() {
            @Override
            public void run() {
                try {
                    statement = connection.createStatement();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        return statement;
    }
}
