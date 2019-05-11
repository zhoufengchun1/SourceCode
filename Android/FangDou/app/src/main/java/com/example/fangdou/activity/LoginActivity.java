package com.example.fangdou.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity
{

    @BindView(R.id.user_text)
    EditText userText;
    @BindView(R.id.passwd_text)
    EditText passwdText;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.userTextInputLayout)
    TextInputLayout userTextInputLayout;
    @BindView(R.id.passwdTextInputLayout)
    TextInputLayout passwdTextInputLayout;

    private String username, password;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    public void init()
    {
        userText.setHint("用户名");
        passwdText.setHint("密码");
        userTextInputLayout.setHintTextAppearance(R.style.TextInputAppTheme);
        passwdTextInputLayout.setHintTextAppearance(R.style.TextInputAppTheme);


    }

    public void setConnection()
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


    public boolean judge()
    {
        if (username.equals(""))
        {
            Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals(""))
        {
            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.login)
    public void onLoginClicked()
    {
        username = userText.getText().toString().trim();
        password = passwdText.getText().toString().trim();
        final boolean isAllow = judge();
        new Thread()
        {
            public void run()
            {
                Looper.prepare();
                if (isAllow)
                {
                    setConnection();
                    try
                    {
                        setConnection();
                        resultSet = statement.executeQuery("select * from user where user_name=" + "'" + username + "'");
                        resultSet.last();
                        if (resultSet.getRow() == 0)
                        {
                            Toast.makeText(LoginActivity.this, "未找到该用户", Toast.LENGTH_LONG).show();
                        } else
                        {
                            resultSet.beforeFirst();
                            while (resultSet.next())
                            {
                                if (resultSet.getString("user_passwd").equals(password))
                                {
                                    Toast.makeText(LoginActivity.this, "欢迎," + username, Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (resultSet.isAfterLast())
                            {
                                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Looper.loop();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                    } finally
                    {
                        try
                        {
                            statement.close();
                            connection.close();
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }.start();
    }

    @OnClick(R.id.register)
    public void onRegisterClicked()
    {
        username = userText.getText().toString().trim();
        password = passwdText.getText().toString().trim();
        final boolean isAllow = judge();
        new Thread()
        {
            public void run()
            {
                Looper.prepare();
                if (isAllow)
                {
                    setConnection();
                    try
                    {
                        resultSet = statement.executeQuery("select * from user where user_name=" + "'" + username + "'");
                        resultSet.last();
                        if (resultSet.getRow() != 0)
                        {
                            Toast.makeText(LoginActivity.this, "用户已存在！", Toast.LENGTH_LONG).show();
                        } else
                        {
                            statement.executeUpdate("insert into user " + "(" + "user_name,user_passwd" + ") " + "values"
                                    + "('" + username + "','" + password + "')");
                            Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                        }
                        Looper.loop();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                    } finally
                    {
                        try
                        {
                            statement.close();
                            connection.close();
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }
}
