using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Forms;

namespace PMS
{
    public class Query
    {
        private string defaultString;
        private List<People> list;
        private DataGridView dataGridView;
        private SqlConnection sqlConnection = SetConnection.sqlConnection;
        
        
        public Query(string defaultString,List<People> list,DataGridView dataGridView)
        {
            this.list = list;
            this.defaultString = defaultString;
            this.dataGridView = dataGridView;
        }

        public void ExecuteQuery()
        {
            SqlDataReader sqlDataReader = null;
            StringBuilder stringBuilder = new StringBuilder(defaultString);
            for (int i = list.Count - 1; i >= 0; i--)
            {
                if (list[i].attr == "")
                {
                    list.Remove(list[i]);
                }
            }

            if (list.Count == 0)
            {
                string[] str = Regex.Split(defaultString, " ", RegexOptions.IgnoreCase);
                SqlCommand sqlCommand = new SqlCommand("select * from "+str[3], sqlConnection);
                sqlDataReader = sqlCommand.ExecuteReader();
                if (sqlDataReader.HasRows)
                {
                    BindingSource bindingSource = new BindingSource();
                    bindingSource.DataSource = sqlDataReader;
                    dataGridView.DataSource = bindingSource;
                }
                else
                    MessageBox.Show("没有数据");
            }
            else
            {
                for (int i = 0; i < list.Count; i++)
                {
                    stringBuilder.Append(list[i].name + " = '" + list[i].attr + "'");
                    if (i != list.Count - 1)
                    {
                        stringBuilder.Append(" and ");
                    }
                }

                SqlCommand sqlCommand = new SqlCommand(stringBuilder.ToString(), sqlConnection);
                sqlDataReader = sqlCommand.ExecuteReader();
                if (sqlDataReader.HasRows)
                {
                    BindingSource bindingSource = new BindingSource();
                    bindingSource.DataSource = sqlDataReader;
                    dataGridView.DataSource = bindingSource;
                }
                else
                {
                    MessageBox.Show("未找到数据！");
                }

                if (sqlDataReader != null)
                {
                    sqlDataReader.Close();
                }
            }
        }
    }
}