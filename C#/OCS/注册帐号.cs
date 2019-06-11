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
        private string verificationcode;


        public 注册帐号(Form form)
        {
            InitializeComponent();
            this.form = form;
            this.StartPosition = FormStartPosition.CenterScreen;
            this.MaximizeBox = false;
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
            if (textBox1.Text == "" || textBox2.Text == "" || textBox3.Text == "" || textBox4.Text == "")
            {
                MessageBox.Show("请键入全部信息。", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
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

                            if (!Regex.IsMatch(textBox5.Text,
                                "^\\s*([A-Za-z0-9_-]+(\\.\\w+)*@(\\w+\\.)+\\w{2,5})\\s*$"))
                            {
                                MessageBox.Show("请输入正确的邮箱格式！\n 邮箱是找回密码的唯一凭据，请正确输入。", "注册失败", MessageBoxButtons.OK,
                                    MessageBoxIcon.Error);
                            }
                            else if (textBox3.Text != textBox4.Text)
                            {
                                MessageBox.Show("两次输入的密码不一致！", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            }
                            else if (!textBox6.Text.Equals(verificationcode))
                            {
                                MessageBox.Show("验证码错误,请检查邮箱是否填写并正确重新发送验证码", "错误", MessageBoxButtons.OK,
                                    MessageBoxIcon.Error);
                            }
                            else
                            {
                                cmd =
                                    "insert into user values (@userid,@username,@userpasswd,@userclass,@userdepname,@usersex,@userEmail)";
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
                                mySqlCommand.Parameters.Add("@usersex", MySqlDbType.String);
                                mySqlCommand.Parameters["@usersex"].Value ="男";
                                mySqlCommand.Parameters.Add("@userEmail", MySqlDbType.String);
                                mySqlCommand.Parameters["@userEmail"].Value = textBox5.Text;
                                mySqlCommand.ExecuteNonQuery();
                                MessageBox.Show("注册成功!", "提示");
                                button2.PerformClick();
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
        }


        private void button2_Click(object sender, EventArgs e)
        {
            this.Hide();
            form.Show();
        }


        private void 注册帐号_FormClosing(object sender, FormClosingEventArgs e)
        {
            DialogResult dialog =
                MessageBox.Show("是否退出程序？", "退出程序", MessageBoxButtons.YesNo, MessageBoxIcon.Exclamation);
            if (dialog == DialogResult.Yes)
                Environment.Exit(0);
            else
            {
                e.Cancel = true;
            }
        }

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if ((e.KeyChar != '\b') && (!Char.IsLetter(e.KeyChar)) && (!char.IsDigit(e.KeyChar)))
            {
                e.Handled = true;
            }
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != '\b') //这是允许输入退格键  
            {
                if ((e.KeyChar < '0') || (e.KeyChar > '9')) //这是允许输入0-9数字  
                {
                    e.Handled = true;
                }
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (!Regex.IsMatch(textBox5.Text,
                "^\\s*([A-Za-z0-9_-]+(\\.\\w+)*@(\\w+\\.)+\\w{2,5})\\s*$"))
            {
                MessageBox.Show("请输入正确的邮箱格式！\n 邮箱是找回密码的唯一凭据，请正确输入。", "注册失败", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
            }
            else
            {
                try
                {
                    verificationcode = MailService.SendMail(textBox5.Text);
                    MessageBox.Show("发送成功！");
                }
                catch (Exception exception)
                {
                    MessageBox.Show("发送失败！");
                    throw;
                }
            }
        }

        private void textBox6_KeyPress(object sender, KeyPressEventArgs e)
        {
            if ((e.KeyChar != '\b') && (!Char.IsLetter(e.KeyChar)) && (!char.IsDigit(e.KeyChar)))
                if (e.KeyChar != '\b' && !Char.IsLetter(e.KeyChar) && !char.IsDigit(e.KeyChar))
                {
                    e.Handled = true;
                }

            e.KeyChar = Convert.ToChar(e.KeyChar.ToString().ToUpper());
        }

 
    }
}