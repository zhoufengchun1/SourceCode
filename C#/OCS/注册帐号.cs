using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using PMS;

namespace OCS
{
    public partial class 注册帐号 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;


        public 注册帐号()
        {
            InitializeComponent();
        }

        private void textBox3_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != '\b')//这是允许输入退格键  
            {
                if ((e.KeyChar < '0') || (e.KeyChar > '9'))//这是允许输入0-9数字  
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
            Regex rg = new Regex("^[\u4e00-\u9fa5]$");  //正则表达式
            if (rg.IsMatch(e.KeyChar.ToString()) && e.KeyChar != '\b') //'\b'是退格键
            {
                e.Handled = true;
            }
        }

        private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
        {
            Regex rg = new Regex("^[\u4e00-\u9fa5]$");  //正则表达式
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
            mySqlCommand.Parameters.Add("@name", MySqlDbType.String);
            mySqlCommand.Parameters["@userid"].Value = textBox1.Text;
            try
            {
                MySqlDataReader reader = mySqlCommand.ExecuteReader();
                if (reader.HasRows)
                {
                    
                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }
        }
    }
}