using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 移动分组 : Form
    {
        public delegate void MoveNode(string result);
        private string str;
        private string group;
        private List<string> list=好友与聊天.group;
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;


        public 移动分组(string str, string group)
        {
            this.str = str;
            this.group = group;
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.MaximizeBox = false;
        }

        private void 移动分组_Load(object sender, EventArgs e)
        {
            labelUserId.Text = str;
            userGroup.Text = group;
            foreach (string s in list)
            {
                comboBox1.Items.Add(s);
            }
            comboBox1.SelectedIndex = 0;

        }


        private void button1_Click(object sender, EventArgs e)
        {
            string friendId = Regex.Replace(str, @"(.*\()(.*)(\).*)", "$2");
            string cmd =
                "update relationship set userGroup=@userGroup where friendId=@friendId;";
            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@friendId", MySqlDbType.Int16);
            mySqlCommand.Parameters["@friendId"].Value = friendId;
            mySqlCommand.Parameters.Add("@userGroup", MySqlDbType.String);
            mySqlCommand.Parameters["@userGroup"].Value = comboBox1.Text;

            if (mySqlCommand.ExecuteNonQuery() == 1)
            {
                MoveNode moveNode = ((好友与聊天)Application.OpenForms["好友与聊天"]).MoveNode;
                moveNode(comboBox1.Text);//委托，将移动事件交给上一个窗口处理
                MessageBox.Show("移动成功！");
                Hide();
            }
            else
            {
                MessageBox.Show("移动失败。", "失败", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
        
    }
}