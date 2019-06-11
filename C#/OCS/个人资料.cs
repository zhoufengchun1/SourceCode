using System;
using System.Data;
using System.Diagnostics;
using System.Text.RegularExpressions;
using MySql.Data.MySqlClient;
using System.Windows.Forms;
using PMS;

namespace OCS
{
    public partial class 个人管理 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;
        private User user;
        private string userName, userSex, userClass, userDep, userEmail;
        private int userId;

        public 个人管理(User user)
        {
            this.user = user;
            InitializeComponent();
            comboBoxSex.SelectedIndex = 0;
            
        }

        private void 个人管理_Load(object sender, System.EventArgs e)
        {
            try
            {
                Query();
            }
            catch (Exception exception)
            {
                Debug.WriteLine(exception);
            }
        }

        private void Query()
        {
            string cmd = "select * from user where userId=@userId";

            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
            mySqlCommand.Parameters["@userId"].Value = user.UserId;

            MySqlDataAdapter mySqlDataAdapter = new MySqlDataAdapter();
            mySqlDataAdapter.SelectCommand = mySqlCommand;
            DataSet dataSet = new DataSet();
            mySqlDataAdapter.Fill(dataSet, "user");
            DataRow dataRow = dataSet.Tables[0].Rows[0];
            userName = dataRow["userName"].ToString();
            userClass = dataRow["userClass"].ToString();
            userSex = dataRow["userSex"].ToString();
            userDep = dataRow["userDepName"].ToString();
            userEmail = dataRow["userEmail"].ToString();

            textBoxId.Text = user.UserId;
            textBoxName.Text = userName;
            textBoxClass.Text = userClass;
            comboBoxSex.Text = userSex;
            textBoxDep.Text = userDep;
            textBoxEmail.Text = userEmail;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            textBoxName.Text = userName;
            textBoxClass.Text = userClass;
            comboBoxSex.Text = userSex;
            textBoxDep.Text = userDep;
            textBoxEmail.Text = userEmail;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (!Regex.IsMatch(textBoxEmail.Text,
                "^\\s*([A-Za-z0-9_-]+(\\.\\w+)*@(\\w+\\.)+\\w{2,5})\\s*$"))
            {
                MessageBox.Show("请输入正确的邮箱格式！\n 邮箱是找回密码的唯一凭据，请正确输入。", "更改失败", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
                return;
            }

            if (textBoxName.Text == "")
            {
                MessageBox.Show("未输入用户名,将使用默认用户名。");
                textBoxName.Text = "菜虚坤";
            }
            else if (textBoxClass.Text == "")
            {
                MessageBox.Show("未设置班级。");
            }
            else if (comboBoxSex.Text == "")
            {
                MessageBox.Show("未设置性别。");
                comboBoxSex.Text = "男";
            }
            else if (textBoxDep.Text == "")
            {
                MessageBox.Show("未设置系名。");
            }

            string cmd =
                "update user set userName=@userName,userSex=@userSex,userClass=@userClass,userDepname=@userDepname,userEmail=@userEmail where userId=@userId";
            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
            mySqlCommand.Parameters["@userId"].Value = user.UserId;
            mySqlCommand.Parameters.Add("@userName", MySqlDbType.String);
            mySqlCommand.Parameters["@userName"].Value = textBoxName.Text;
            mySqlCommand.Parameters.Add("@userSex", MySqlDbType.String);
            mySqlCommand.Parameters["@userSex"].Value = comboBoxSex.Text;
            mySqlCommand.Parameters.Add("@userClass", MySqlDbType.String);
            mySqlCommand.Parameters["@userClass"].Value = textBoxClass.Text;
            mySqlCommand.Parameters.Add("@userDepname", MySqlDbType.String);
            mySqlCommand.Parameters["@userDepname"].Value = textBoxDep.Text;
            mySqlCommand.Parameters.Add("@userEmail", MySqlDbType.String);
            mySqlCommand.Parameters["@userEmail"].Value = textBoxEmail.Text;
            if (mySqlCommand.ExecuteNonQuery() != 0)
            {
                MessageBox.Show("修改成功！", "成功", MessageBoxButtons.OK);
                Query();
            }
            else
            {
                MessageBox.Show("修改失败。", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}