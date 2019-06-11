using System;
using System.Data;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS.Resources
{
    public partial class 查看资料 : Form
    {
        private string userId;
        private MySqlConnection mySqlConnection = SetConnection.mySqlConnection;

        public 查看资料(string userId)
        {
            this.userId = userId;
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.MaximizeBox = false;
        }

        private void 查看资料_Load(object sender, EventArgs e)
        {
            string cmd = "select * from user where userId=@userId";

            MySqlCommand mySqlCommand = new MySqlCommand(cmd, mySqlConnection);
            mySqlCommand.Parameters.Add("@userId", MySqlDbType.Int16);
            mySqlCommand.Parameters["@userId"].Value = userId;

            MySqlDataAdapter mySqlDataAdapter = new MySqlDataAdapter();
            mySqlDataAdapter.SelectCommand = mySqlCommand;

            DataSet dataSet = new DataSet();
            mySqlDataAdapter.Fill(dataSet, "user");
            DataRow dataRow = dataSet.Tables[0].Rows[0];
            labelId.Text = dataRow["userId"].ToString();
            labelName.Text = dataRow["userName"].ToString();
            labelClass.Text = dataRow["userClass"].ToString();
            labelSex.Text = dataRow["userSex"].ToString();
            labelDep.Text = dataRow["userDepName"].ToString();
            labelEmail.Text = dataRow["userEmail"].ToString();
        }
    }
}