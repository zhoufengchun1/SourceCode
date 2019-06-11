using System;
using System.Drawing;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using Org.BouncyCastle.Asn1.Cms;
using PMS;

namespace OCS
{
    public partial class 添加好友 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;
        private string Name, attr;
        private MySqlDbType mySqlDbType;
        private string userId;
        public string group;

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


        private void button2_Click(object sender, EventArgs e)
        {
            if (listView1.SelectedItems.Count > 0)
            {
                
                好友分组 form = new 好友分组();
                form.ShowDialog(this);
                string cmd = "update relationship set userGroup=@Group where friendId=@userId";
                MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
                mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
                mySqlCommand.Parameters["@userId"].Value = userId;
                if (mySqlCommand.ExecuteNonQuery() == 0)
                {
                    MessageBox.Show("添加失败!", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    
                }
                
            }
            else
            {
                MessageBox.Show("没有选中用户！", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            listView1.Items.Clear();
            if (textBox1.Visible && textBox1.Text == "")
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
            else
            {
                listView1.Items[0].Selected = true;
                listView1.Select();
            }

            mySqlDataReader.Close();
        }

        private void listView1_ColumnClick(object sender, ColumnClickEventArgs e)
        {
            if (listView1.Items.Count != 0)
            {
                userId = listView1.Items[e.Column].Text;
            }
        }
    }
}