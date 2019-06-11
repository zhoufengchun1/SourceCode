using System;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 忘记密码 : Form
    {
        private Form form;
        private string userName;
        
        private string verificationcode;
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;


        public 忘记密码(Form form)
        {
            InitializeComponent();
            this.form = form;
            this.MaximizeBox = false;
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }


        private void textBox3_KeyPress(object sender, KeyPressEventArgs e)
        {
            if ((e.KeyChar != '\b') &&(!Char.IsLetter(e.KeyChar)) && (!char.IsDigit(e.KeyChar)))
            if (e.KeyChar != '\b' && !Char.IsLetter(e.KeyChar) && !char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }

            e.KeyChar = Convert.ToChar(e.KeyChar.ToString().ToUpper());
        }

        private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
        {
            Regex rg = new Regex("^[\u4e00-\u9fa5]$"); //正则表达式
            if (rg.IsMatch(e.KeyChar.ToString()) && e.KeyChar != '\b') //'\b'是退格键
            {
                e.Handled = true;
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            this.Hide();
            form.Show();
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (!Regex.IsMatch(textBox2.Text,
               "^\\s*([A-Za-z0-9_-]+(\\.\\w+)*@(\\w+\\.)+\\w{2,5})\\s*$"))
            {
                MessageBox.Show("请输入正确的邮箱格式！\n 邮箱是找回密码的唯一凭据，请正确输入。", "注册失败", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
            }
            else
            {
                userName = textBox1.Text;
                
                try
                {
                    verificationcode = MailService.SendMail(textBox2.Text);
                    MessageBox.Show("发送成功！");
                }
                catch (Exception exception)
                {
                    MessageBox.Show("发送失败！");
                    throw;
                }
            }
        }


        private void button2_Click(object sender, EventArgs e)
        {
            if (textBox1.Text == "" || textBox2.Text == "")
            {
                MessageBox.Show("请输入正确的信息！", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else if (textBox3.Text == "")
            {
                MessageBox.Show("请输入验证码！", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else if (!textBox3.Text.Equals(verificationcode))
            {
                MessageBox.Show("验证码错误,请检查邮箱是否填写并正确重新发送验证码", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else if (textBox4.Text == "")
            {
                MessageBox.Show("请输入新密码！", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                string cmd = "update user set userPasswd=@userPasswd where userId=@userId";
                MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
                mySqlCommand.Parameters.Add("@userPasswd", MySqlDbType.String);
                mySqlCommand.Parameters["@userPasswd"].Value = textBox4.Text;
                mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
                mySqlCommand.Parameters["@userId"].Value = textBox1.Text;

                if (mySqlCommand.ExecuteNonQuery() == 0)
                {
                    MessageBox.Show("未找到该用户。", "更改失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else
                {
                    MessageBox.Show("更改成功！", "成功");
                    button3.PerformClick();
                }
            }
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            
            if (checkBox1.Checked)
            {
                //复选框被勾选，明文显示
                textBox4.PasswordChar = new char();
            }
            else
            {
                //复选框被取消勾选，密文显示
                textBox4.PasswordChar = '*';
            }
        }
    }
}
