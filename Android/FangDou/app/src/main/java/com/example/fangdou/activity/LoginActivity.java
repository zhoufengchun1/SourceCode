package com.example.fangdou.activity;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
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
    @BindView(R.id.autoLogin)
    AppCompatCheckBox autoLogin;
    @BindView(R.id.savePasswd)
    CheckBox savePasswd;
    @BindView(R.id.text)
    LinearLayout text;
    @BindView(R.id.logoff)
    TextView logoff;


    private String username, password;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean isSuccess = false;
    private boolean isSave = false;
    private boolean isAuto = false;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("个人设置");
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_default));
        ThemeActivity.setStatusBarColor(LoginActivity.this, getResources().getColor(R.color.color_default));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        init();
    }

    public void init()
    {
        userTextInputLayout.setHint("用户名");
        passwdTextInputLayout.setHint("密码");
        userTextInputLayout.setHintTextAppearance(R.style.TextInputAppTheme);
        passwdTextInputLayout.setHintTextAppearance(R.style.TextInputAppTheme);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo.xml", MODE_PRIVATE);

        isFirst = sharedPreferences.getBoolean("isFirst", true);
        if (sharedPreferences.getBoolean("isFirst", true))
        {
            autoLogin.setChecked(false);
            savePasswd.setChecked(false);
        } else
        {
            isAuto = sharedPreferences.getBoolean("isAuto", false);
            autoLogin.setChecked(isAuto);
            isSave = sharedPreferences.getBoolean("isSave", false);
            savePasswd.setChecked(isSave);
        }
        savePasswd.setChecked(isSave);
        savePasswd.setOnCheckedChangeListener(this);
        autoLogin.setChecked(isAuto);
        autoLogin.setOnCheckedChangeListener(this);
        if (isSave)
        {
            userText.setText(sharedPreferences.getString("User_Name", ""));
            passwdText.setText(sharedPreferences.getString("User_PassWord", ""));
        }

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
        if (!checkNetwork())
        {
            Toast.makeText(LoginActivity.this, "网络不可用，请检查网络连接！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.equals(""))
        {
            Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals(""))
        {
            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.length() > 10 || password.length() > 10)
        {
            Toast.makeText(LoginActivity.this, "用户名、密码均为小于10位的数字或者字母。", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.login)
    public void onLoginClicked()
    {
        username = userText.getText().toString().trim();
        password = passwdText.getText().toString().trim();
        boolean isAllow = judge();
        if (isAllow)
        {
            new Thread()
            {
                public void run()
                {
                    Looper.prepare();

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
                                    isSuccess = true;
                                    saveInfo(false);
                                    onBack();
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
            }.start();
        }
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
                            saveInfo(true);
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

    public boolean checkNetwork()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
        {
            return networkInfo.isAvailable();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return true;
    }

    public void saveInfo(Boolean isFirst)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo.xml", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User_Name", username);
        editor.putString("User_PassWord", password);
        editor.putBoolean("isAuto", autoLogin.isChecked());
        editor.putBoolean("F", isFirst);//取消第一次登录
        editor.putBoolean("isSave", savePasswd.isChecked());
        editor.putBoolean("isLogin", true);
        editor.apply();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (buttonView == savePasswd)
        {  //记住密码选框发生改变时
            if (!isChecked)
            {   //如果取消“记住密码”，那么同样取消自动登陆
                autoLogin.setChecked(false);
            }
        } else if (buttonView == autoLogin)
        {   //自动登陆选框发生改变时
            if (isChecked)
            {   //如果选择“自动登录”，那么同样选中“记住密码”
                savePasswd.setChecked(true);
            }
        }

    }

    public void onBack()//模拟点击返回键
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e)
                {
                    Log.e("Exception when onBack", e.toString());
                }
            }
        }.start();

    }

    @OnClick(R.id.logoff)
    public void onViewClicked()
    {
        AlertDialog.Builder logoffDialog = new AlertDialog.Builder(LoginActivity.this);
        logoffDialog.setTitle("注销账号");
        logoffDialog.setMessage("注销帐号操作不可撤回，请确认");
        logoffDialog.setPositiveButton("去确认信息", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                startActivity(new Intent(LoginActivity.this, LogoffActivity.class));
            }
        }).show();

    }
}
