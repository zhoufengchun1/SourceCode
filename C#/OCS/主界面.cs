using System;
using System.Data;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    public partial class 主界面 : Form
    {
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;
        private User user;

        public 主界面(User user)
        {
            InitializeComponent();
            this.user = user;
            this.MaximizeBox = false;
            this.StartPosition = FormStartPosition.CenterScreen;
        }

        private void 主界面_Load(object sender, System.EventArgs e)
        {
            treeView1.LabelEdit = true;

            try
            {
                string cmd = "SELECT distinct userGroup  from relationshipPlus where userId=@userId;"
                             + "SELECT friendId AS friends,userGroup as myGroup,userName as userName,friendName as friendName from relationshipPlus  WHERE userId =@userId  union ALL SELECT userId as friends, friendGroup as myGroup,friendName as userName,userName as friendName  from relationshipPlus  WHERE friendId = @userId";

                MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
                mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
                mySqlCommand.Parameters["@userId"].Value = user.UserId;

                MySqlDataAdapter mySqlDataAdapter = new MySqlDataAdapter();
                mySqlDataAdapter.SelectCommand = mySqlCommand;
                
                DataSet dataSet = new DataSet();
                mySqlDataAdapter.Fill(dataSet, "relationshipPlus");
                foreach (DataRow dataRow in dataSet.Tables[0].Rows)
                {
                    TreeNode treeNode = new TreeNode();
                    treeNode.Text = dataRow["userGroup"].ToString();
                    treeView1.Nodes.Add(treeNode);
                    foreach (DataRow row in dataSet.Tables[1].Rows)
                    {
                        if (row["myGroup"].ToString().Equals(treeNode.Text))
                        {
                            treeNode.Nodes.Add(new TreeNode(row["friendName"] + "(" + row["friends"] + ")"));
                            break;
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }


            /*MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
            mySqlCommand.Parameters["@userId"].Value = user.UserId;
            try
            {
                using (MySqlDataReader reader = mySqlCommand.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        string Title = reader.GetString("userGroup");
                        TreeNode node = new TreeNode();
                        node.Text = Title;
                        treeView1.Nodes.Add(node);
                        cmd =
                            "SELECT friendId AS friends,userGroup as myGroup  from relationship  WHERE userId = @userId "
                            + "union ALL SELECT userId as friends, friendGroup   as myGroup  from relationship  WHERE friendid = @userId";
                        
                    }
                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }*/
        }

        private void 主界面_FormClosing(object sender, FormClosingEventArgs e)
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
    }
}