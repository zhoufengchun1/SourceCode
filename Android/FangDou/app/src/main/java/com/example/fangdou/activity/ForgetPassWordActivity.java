package com.example.fangdou.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fangdou.JDBC;
import com.example.fangdou.R;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPassWordActivity extends AppCompatActivity
{

    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_passwd)
    EditText userPasswd;
    @BindView(R.id.selectDate)
    Button selectDate;
    @BindView(R.id.okay)
    Button okay;
    private String user_name;
    private String user_passwd;
    private String user_date;
    private Connection connection = JDBC.connection;
    private Statement statement = JDBC.statement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpasswd);
        ButterKnife.bind(this);
    }

    public void init()
    {
        user_name = userName.getText().toString();
        user_passwd = userPasswd.getText().toString();

        new Thread() {
            @Override
            public void run() {
                try {
                    statement.executeQuery("select ");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @OnClick({R.id.selectDate, R.id.okay})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.selectDate:
                break;
            case R.id.okay:
                break;
        }
    }
}

