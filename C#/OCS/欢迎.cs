using System;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using PMS;

namespace OCS
{
    
    public partial class 欢迎 : Form
    {
        
        public MySqlConnection MySqlConnection = SetConnection.GetConnection();
        
        public 欢迎()
        {
            InitializeComponent();
            
        }

        private void label2_Click(object sender, EventArgs e)
        {
            throw new System.NotImplementedException();
        }
    }
}