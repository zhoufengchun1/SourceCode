using System;
using System.Data.SqlClient;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace PMS
{
    public static class SetConnection
    {
        public static MySqlConnection mySqlConnection = null;
        public static string user = "root";
        public static string passwd = "admin";
        
        public static MySqlConnection GetConnection()
        {
            try
            {
                string myConnString =
                    "server=47.100.195.173;port=3306;user="+user+";password="+passwd+";database=OCS";
                Console.WriteLine("已建立连接");
                mySqlConnection=new MySqlConnection(myConnString);
                mySqlConnection.Open();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                MessageBox.Show("数据库连接错误。");
            }

            return mySqlConnection;
        }
    }
}