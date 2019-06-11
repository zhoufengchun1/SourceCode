using System;
using System.Drawing;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 添加好友 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;
        private string Name, attr;
        private MySqlDbType mySqlDbType;


        public 添加好友()
        {
            InitializeComponent();
            comboBox1.SelectedIndex = 0;
            comboBox2.SelectedIndex = 0;
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
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
            listView1.Items.Clear();
            if (textBox1.Visible&& textBox1.Text == "")
            {
                MessageBox.Show("请输入查询数据!", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            switch (comboBox1.Text)
            {
                case "学号/工号":
                    Name = "userId";
                    mySqlDbType = MySqlDbType.Int16;
                    break;
                case "用户名":
                    Name = "userName";
                    mySqlDbType = MySqlDbType.String;
                    break;
                case "性别":
                    Name = "userSex";
                    mySqlDbType = MySqlDbType.String;
                    break;
                case "所在班级":
                    Name = "userClass";
                    mySqlDbType = MySqlDbType.String;
                    break;
                case "所在系":
                    Name = "userDepname";
                    mySqlDbType = MySqlDbType.String;
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
                "select * from user where " + Name + "=@attr";
            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@attr", mySqlDbType);
            mySqlCommand.Parameters["@attr"].Value = attr;

            MySqlDataReader mySqlDataReader = mySqlCommand.ExecuteReader();
            while (mySqlDataReader.Read())
            {
                ListViewItem listViewItem = new ListViewItem();
//                listViewItem.Text = mySqlDataReader["userId"].ToString();
                listViewItem.SubItems.Add(mySqlDataReader["userId"].ToString());
                listViewItem.SubItems.Add(mySqlDataReader["userName"].ToString());
                listViewItem.SubItems.Add(mySqlDataReader["userSex"].ToString());
                listViewItem.SubItems.Add(mySqlDataReader["userClass"].ToString());
                listViewItem.SubItems.Add(mySqlDataReader["userDepname"].ToString());
                listView1.Items.Add(listViewItem);
            }

            if (listView1.Items.Count == 0)
            {
                MessageBox.Show("未查询到相关数据。");
            }
            mySqlDataReader.Close();
        }
    }
}