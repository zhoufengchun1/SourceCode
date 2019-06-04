using System;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace PMS
{
    public static class SetConnection
    {
        public static SqlConnection sqlConnection = null;
        public static string passwd = "hbtskyh@991027";

        public static SqlConnection GetConnection()
        {
            try
            {
                string myConnString =
                    "Data Source=.;Initial Catalog=PMS;Persist Security Info=True;User ID=sa;Password="
                    + passwd + ";MultipleActiveResultSets=true";
                /*在SqlCommand在每次执行ExecuteNonQuery（）方法之后，
                内部会生成一个空的DataReader对象，
                该对象只有在数据库连接关闭之后，才会被释放掉，
                数据库连接对象是一个静态的全局变量。
                因为在同时调用API进行数据查询时，在第一个查询还未结束，数据库连接对象还未关闭，
                第二个查询却已经开始查询，所以才会出现DataReader没有关闭的情况*/
                sqlConnection = new SqlConnection(myConnString); //实例化连接对象
                sqlConnection.Open();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                MessageBox.Show("数据库连接错误。");
            }

            return sqlConnection;
        }
    }
}