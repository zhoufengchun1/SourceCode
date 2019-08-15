package com.kangyh.student.Utils;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: KangYh
 * @Date: 2019/7/24 21:25
 */
public class DbUtils
{
    public static final String URL = "jdbc:mysql://localhost:3306";
    public static final String userName = "root";
    public static final String passWd = "995264";
    public static PreparedStatement preparedStatement = null;
    public static Connection connection = null;
    public static ResultSet resultSet = null;


    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, userName, passWd);
    }

    public static PreparedStatement createPreparedStatement() throws SQLException, ClassNotFoundException
    {
        preparedStatement = getConnection().prepareStatement();

    }
}
