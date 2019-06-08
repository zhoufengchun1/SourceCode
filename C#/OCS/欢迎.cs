using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 欢迎 : Form
    {
        public MySqlConnection mySqlConnection = SetConnection.GetConnection();
        public 注册帐号 form1;
        public 忘记密码 form2;
        private Dictionary<string, User> users;
        private string userId, userPasswd;

        public 欢迎()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.MaximizeBox = false;

            form1 = new 注册帐号(this);
            form2 = new 忘记密码(this);
            form1.StartPosition = FormStartPosition.CenterScreen;
            form2.StartPosition = FormStartPosition.CenterScreen;
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
            userId = comboBox1.Text;
            userPasswd = textBox2.Text;

            string cmd = "select * from user where userId=@userid";
            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@userid", MySqlDbType.Int16);
            mySqlCommand.Parameters["@userid"].Value = userId;
            try
            {
                using (MySqlDataReader reader = mySqlCommand.ExecuteReader())
                {
                    if (!reader.HasRows)
                    {
                        MessageBox.Show("用户不存在！", "登录失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                    else
                    {
                        reader.Read();
                        if (reader.GetString("userPasswd") == userPasswd)
                        {
                            MessageBox.Show("登录成功！");
                            WriteToFile();
                        }
                        else
                        {
                            MessageBox.Show("密码错误。", "登录失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
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

        private void linkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            this.Hide();
            form1.Show();
        }

        private void 欢迎_FormClosing(object sender, FormClosingEventArgs e)
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

        private void WriteToFile()
        {
            User user = new User();
            FileStream fileStream = new FileStream("user.bin", FileMode.OpenOrCreate);
            BinaryFormatter binaryFormatter = new BinaryFormatter();
            user.UserId = userId;
            user.RememberPasswd = checkBox2.Checked;

            user.UserPasswd = checkBox2.Checked ? userPasswd : "";

            if (users == null)
            {
                users = new Dictionary<string, User>();
            }

            if (users.ContainsKey(user.UserId))
            {
                users.Remove(user.UserId);
            }

            users.Add(user.UserId, user);
            binaryFormatter.Serialize(fileStream, users);
            fileStream.Close();
        }

        private void 欢迎_Load(object sender, EventArgs e)
        {
            //读取文件流对象
            FileStream fs = new FileStream("user.bin", FileMode.OpenOrCreate);
            if (fs.Length > 0)
            {
                BinaryFormatter bf = new BinaryFormatter();
                users = bf.Deserialize(fs) as Dictionary<string, User>;
                //读出存在Data.bin 里的用户信息
                //循环添加到Combox1
                foreach (User user in users.Values)
                {
                    comboBox1.Items.Add(user.UserId);
                }

                //combox1 用户名默认选中第一个
            }

            fs.Close();
        }


        private void comboBox1_SelectedValueChanged(object sender, EventArgs e)
        {
            FileStream fs = new FileStream("user.bin", FileMode.OpenOrCreate);
            if (fs.Length > 0)
            {
                BinaryFormatter bf = new BinaryFormatter();
                users = bf.Deserialize(fs) as Dictionary<string, User>;

                if (users[comboBox1.Text].RememberPasswd)
                {
                    textBox2.Text = users[comboBox1.Text].UserPasswd;
                    checkBox2.Checked = true;
                }
                else
                {
                    textBox2.Text = "";
                    checkBox2.Checked = false;
                }
            }

            fs.Close();
        }

        private void comboBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }

        private void linkLabel2_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            this.Hide();
            form2.Show(this);
        }
    }
}