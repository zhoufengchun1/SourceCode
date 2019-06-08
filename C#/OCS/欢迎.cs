using System;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 欢迎 : Form
    {
        public MySqlConnection mySqlConnection = SetConnection.GetConnection();
        public 注册帐号 form;

        public 欢迎()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.MaximizeBox = false;

            form = new 注册帐号(this);
            form.StartPosition = FormStartPosition.CenterScreen;
        }


        private void textBox1_KeyPress(object sender, KeyPressEventArgs e) //帐号限制
        {
            if ((e.KeyChar != '\b') && (!Char.IsLetter(e.KeyChar)) && (!char.IsDigit(e.KeyChar)))
            {
                e.Handled = true;
            }
        }

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e) //密码限制
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
                textBox2.PasswordChar = new char();
            }
            else
            {
                //复选框被取消勾选，密文显示
                textBox2.PasswordChar = '*';
            }
        }

        private void 登录_Click(object sender, EventArgs e)
        {
            string userName = textBox1.Text;
            string passWd = textBox2.Text;
            string cmd = "select * from user where userId=@userid";
            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@userid", MySqlDbType.Int16);
            mySqlCommand.Parameters["@userid"].Value = textBox1.Text;
        }

        private void linkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            this.Hide();
            form.Show();
        }
    }
}