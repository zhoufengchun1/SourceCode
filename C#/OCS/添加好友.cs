using System;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 添加好友 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;
        private string Name, attr;
       
        public 添加好友()
        {
            InitializeComponent();
            comboBox1.SelectedIndex = 0;
            comboBox2.SelectedIndex = 0;
        }

        private void comboBox1_SelectedIndexChanged(object sender, System.EventArgs e)
        {
            if (comboBox1.Text.Equals("性别"))
            {
                textBox1.Visible = false;
                comboBox2.Visible = true;
            }
            else
            {
                textBox1.Visible = true;
                comboBox2.Visible = false;
            }
        }

        private void 添加好友_Load(object sender, EventArgs e)
        {
            // TODO: 这行代码将数据加载到表“oCSDataSet.user”中。您可以根据需要移动或删除它。
            this.userTableAdapter.Fill(this.oCSDataSet.user);
            // TODO: 这行代码将数据加载到表“oCSDataSet.user”中。您可以根据需要移动或删除它。
            this.userTableAdapter.Fill(this.oCSDataSet.user);
            // TODO: 这行代码将数据加载到表“oCSDataSet.user”中。您可以根据需要移动或删除它。
            this.userTableAdapter.Fill(this.oCSDataSet.user);
            // TODO: 这行代码将数据加载到表“oCSDataSet.user”中。您可以根据需要移动或删除它。
            this.userTableAdapter.Fill(this.oCSDataSet.user);

        }

        private void button1_Click(object sender, EventArgs e)
        {
            switch (comboBox1.Text)
            {
                case "学号/工号":
                    Name = "userId";
                    break;
                case "用户名":
                    Name = "userName";
                    break;
                case "性别":
                    Name = "userSex";
                    break;
                case "所在班级":
                    Name = "userClasd";
                    break;
                case "所在系":
                    Name = "userDepname";
                    break;
            }

            if (!comboBox1.Text.Equals("性别"))
            {
                attr = textBox1.Text;
            }
            else
            {
                attr = comboBox2.Text;
            }

            string cmd =
                "select * from user where @Name=@attr";
            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@Name", MySqlDbType.Int16);
            mySqlCommand.Parameters["@Name"].Value = Name;
            mySqlCommand.Parameters.Add("@attr", MySqlDbType.Int16);
            mySqlCommand.Parameters["@attr"].Value = attr;
        }
    }
}