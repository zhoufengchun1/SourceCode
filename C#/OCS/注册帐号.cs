using System;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 注册帐号 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;
        private Form form;

        public 注册帐号(Form form)
        {
            InitializeComponent();
            this.form = form;
        }

        private void textBox3_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != '\b') //这是允许输入退格键  
            {
                if ((e.KeyChar < '0') || (e.KeyChar > '9')) //这是允许输入0-9数字  
                {
                    e.Handled = true;
                }
            }
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }

        private void textBox3_KeyPress_1(object sender, KeyPressEventArgs e)
        {
            Regex rg = new Regex("^[\u4e00-\u9fa5]$"); //正则表达式
            if (rg.IsMatch(e.KeyChar.ToString()) && e.KeyChar != '\b') //'\b'是退格键
            {
                e.Handled = true;
            }
        }

        private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
        {
            Regex rg = new Regex("^[\u4e00-\u9fa5]$"); //正则表达式
            if (rg.IsMatch(e.KeyChar.ToString()) && e.KeyChar != '\b') //'\b'是退格键
            {
                e.Handled = true;
            }
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox1.Checked)
            {
                //复选框被勾选，明文显示
                textBox3.PasswordChar = new char();
                textBox4.PasswordChar = new char();
            }
            else
            {
                //复选框被取消勾选，密文显示
                textBox3.PasswordChar = '*';
                textBox4.PasswordChar = '*';
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string cmd = "select * from user where userId=@userid";
            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@userid", MySqlDbType.Int16);
            mySqlCommand.Parameters["@userid"].Value = textBox1.Text;
            try
            {
                using (MySqlDataReader reader = mySqlCommand.ExecuteReader())
                {
                    if (reader.HasRows)
                    {
                        MessageBox.Show("用户已存在！", "注册失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                    else
                    {
                        reader.Close();
                        if (textBox3.Text != textBox4.Text)
                        {
                            MessageBox.Show("两次输入的密码不一致！", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                        else
                        {
                            cmd =
                                "insert into user values (@userid,@username,@userpasswd,@userclass,@userdepname,@usersex)";
                            mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
                            mySqlCommand.Parameters.Add("@userid", MySqlDbType.Int16);
                            mySqlCommand.Parameters["@userid"].Value = textBox1.Text;
                            mySqlCommand.Parameters.Add("@username", MySqlDbType.String);
                            mySqlCommand.Parameters["@username"].Value = textBox2.Text;
                            mySqlCommand.Parameters.Add("@userpasswd", MySqlDbType.String);
                            mySqlCommand.Parameters["@userpasswd"].Value = textBox3.Text;
                            mySqlCommand.Parameters.Add("@userclass", MySqlDbType.String);
                            mySqlCommand.Parameters["@userclass"].Value = "未设置";
                            mySqlCommand.Parameters.Add("@userdepname", MySqlDbType.String);
                            mySqlCommand.Parameters["@userdepname"].Value = "未设置";
                            mySqlCommand.Parameters.Add("@usersex", MySqlDbType.Int16);
                            mySqlCommand.Parameters["@usersex"].Value = 0;
                            mySqlCommand.ExecuteNonQuery();
                            MessageBox.Show("注册成功!", "提示");
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Hide();
            form.Show();
        }
    }
}