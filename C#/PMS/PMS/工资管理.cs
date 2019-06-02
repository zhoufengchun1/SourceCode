using System;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace PMS
{
    public partial class 工资管理 : Form
    {
        private SqlConnection sqlConnection = SetConnection.GetConnection();

        public 工资管理()
        {
            InitializeComponent();
        }

        private void 工资管理_Load(object sender, EventArgs e)
        {
//            // TODO: 这行代码将数据加载到表“pMSDataSet9.SALARY”中。您可以根据需要移动或删除它。
//            this.sALARYTableAdapter3.Fill(this.pMSDataSet9.SALARY);
//            // TODO: 这行代码将数据加载到表“pMSDataSet8.SALARY”中。您可以根据需要移动或删除它。
//            this.sALARYTableAdapter2.Fill(this.pMSDataSet8.SALARY);
//            // TODO: 这行代码将数据加载到表“pMSDataSet7.SALARY”中。您可以根据需要移动或删除它。
//            this.sALARYTableAdapter1.Fill(this.pMSDataSet7.SALARY);
//            // TODO: 这行代码将数据加载到表“pMSDataSet5.WITHHOLD”中。您可以根据需要移动或删除它。
//            this.wITHHOLDTableAdapter.Fill(this.pMSDataSet5.WITHHOLD);
        }

        private void Button6_Click(object sender, EventArgs e) //工资查询
        {
            String Sno = textBox10.Text.Trim();
            String Sname = textBox9.Text.Trim();
            String Dno = comboBox2.Text.Trim();
            String Paytime = textBox8.Text.Trim();

            if (Sno != "" && Sname == "" && Dno == "" && Paytime == "")
            {
                String select_by_Sno = "select * from SALARY where Sno='" + Sno + "'";
                SqlCommand sqlCommand = new SqlCommand(select_by_Sno, sqlConnection);
                SqlDataReader sqlDataReader = sqlCommand.ExecuteReader();
                BindingSource bindingSource = new BindingSource();
                bindingSource.DataSource = sqlDataReader;
                salarytable.DataSource = bindingSource;
            }
            else if (Sno == "" && Sname != "" && Dno == "" && Paytime == "")
            {
                String select_by_Sname = "select * from SALARY where Sname='" + Sname + "'";
                SqlCommand sqlCommand1 = new SqlCommand(select_by_Sname, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                salarytable.DataSource = bindingSource1;
            }
            else if (Sno == "" && Sname == "" && Dno != "" && Paytime == "")
            {
                String select_by_Dno = "select * from SALARY where Dno='" + Dno + "'";
                SqlCommand sqlCommand1 = new SqlCommand(select_by_Dno, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                salarytable.DataSource = bindingSource1;
            }
            else if (Sno == "" && Sname == "" && Dno == "" && Paytime != "")
            {
                String select_by_Paytime = "select * from SALARY where Paytime='" + Paytime + "'";
                SqlCommand sqlCommand1 = new SqlCommand(select_by_Paytime, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                salarytable.DataSource = bindingSource1;
            }
            else if (Sno == "" && Sname == "" && Dno == "" && Paytime == "")
            {
                String select = "select * from SALARY ";
                SqlCommand sqlCommand1 = new SqlCommand(select, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                salarytable.DataSource = bindingSource1;
            }
            else
            {
                MessageBox.Show("输入有误，请检查!", "警告");
            }

            sqlConnection.Close();
        }

        private void Label12_Click(object sender, EventArgs e)
        {
        }

        private void Button2_Click(object sender, EventArgs e) //写入
        {
            String WITHHOLDID = textBox12.Text.Trim();
            String Sno = textBox5.Text.Trim();
            String RID = textBox7.Text.Trim();
            String WITHHOLDTIME = textBox11.Text.Trim();

            try
            {
                if (WITHHOLDID == "" || Sno == "" || RID == "" || WITHHOLDTIME == "")
                {
                    MessageBox.Show("请将信息填写完整!", "警告");
                }
                else
                {
                    string insertStr = "INSERT INTO  WITHHOLD (WITHHOLDID,Sno,RID,WITHHOLDTIME)    " +
                                       "VALUES ('" + WITHHOLDID + "','" + Sno + "','" + RID + "','" + WITHHOLDTIME +
                                       "')";
                    SqlCommand cmd = new SqlCommand(insertStr, sqlConnection);
                    cmd.ExecuteNonQuery();
                }
            }
            catch
            {
                MessageBox.Show("输入数据违反要求!", "警告");
            }

            this.wITHHOLDTableAdapter.Fill(this.pMSDataSet5.WITHHOLD);
        }

        private void Button3_Click(object sender, EventArgs e)
        {
        }

        private void Button1_Click(object sender, EventArgs e) //删除
        {
            try
            {
                string select_ID = withholdtable.SelectedRows[0].Cells[0].Value.ToString(); //选择的当前行第一列的值，也就是ID
                string delete_by_ID = "delete from WITHHOLD where WITHHOLDID=" + select_ID; //sql删除语句
                SqlCommand cmd = new SqlCommand(delete_by_ID, sqlConnection);
                cmd.ExecuteNonQuery();
            }
            catch
            {
                MessageBox.Show("请正确选择行!");
            }

            this.wITHHOLDTableAdapter.Fill(this.pMSDataSet5.WITHHOLD);
        }

        private void TextBox8_TextChanged(object sender, EventArgs e)
        {
        }

        private void TextBox9_TextChanged(object sender, EventArgs e)
        {
        }

        private void TextBox10_TextChanged(object sender, EventArgs e)
        {
        }

        private void ComboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
        }

        private void Salarytable_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
        }

        private void Label8_Click(object sender, EventArgs e)
        {
        }

        private void Label14_Click(object sender, EventArgs e)
        {
        }

        private void Label17_Click(object sender, EventArgs e)
        {
        }

        private void Label18_Click(object sender, EventArgs e)
        {
        }
    }
}