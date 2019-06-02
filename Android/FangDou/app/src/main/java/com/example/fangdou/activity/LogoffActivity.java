package com.example.fangdou.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fangdou.JDBC;
import com.example.fangdou.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogoffActivity extends AppCompatActivity
{
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_passwd)
    EditText userPasswd;
    @BindView(R.id.user_date)
    EditText userDate;
    @BindView(R.id.okay)
    Button okay;

    private String user_name;
    private String user_passwd;
    private String user_date;
    private Connection connection;
    private Statement statement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoff);
        ButterKnife.bind(this);
        Log.d("haha", userDate.getText().toString());
        new Thread()
        {
            @Override
            public void run()
            {
                connection = JDBC.connSql();
                try
                {
                    statement = connection.createStatement();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }

            }
        }.start();
    }
//
//    private boolean checkInfo() throws SQLException
//    {
//        ResultSet resultSet = statement.executeQuery("select * from user where user_name=" + "'" + userName.getText().toString() + "'");
//        if (resultSet.getRow() == 0)
//        {
//            Toast.makeText(LogoffActivity.this, "用户不存在。", Toast.LENGTH_SHORT).show();
//
//        } else
//        {
//            resultSet.beforeFirst();
//            while (resultSet.next())
//            {
//                if ((resultSet.getString("user_passwd").equals(userPasswd.getText().toString()))
//                        && (resultSet.getString("user_registDate").equals()))
//                {
//
//                }
//            }
//        }
//    }

    @OnClick(R.id.okay)
    public void onViewClicked()
    {
    }
}
