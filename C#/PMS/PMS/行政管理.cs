using System;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace PMS
{
    public partial class 行政管理 : Form
    {
        public 行政管理()
        {
            InitializeComponent();
        }

        private void 行政管理_Load(object sender, EventArgs e)
        {
            // TODO: 这行代码将数据加载到表“pMSDataSet10.RATE”中。您可以根据需要移动或删除它。
//            this.rATETableAdapter.Fill(this.pMSDataSet10.RATE);
//            // TODO: 这行代码将数据加载到表“pMSDataSet4.DEPARTMENT”中。您可以根据需要移动或删除它。
//            this.dEPARTMENTTableAdapter.Fill(this.pMSDataSet4.DEPARTMENT);
        }

        private void Button1_Click(object sender, EventArgs e) //查询
        {
            String Dno = textBox1.Text.Trim();
            String Dname = textBox2.Text.Trim();
            String Dpop = textBox3.Text.Trim();

            String conn = "Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117";
            SqlConnection sqlConnection = new SqlConnection(conn); //实例化连接对象
            sqlConnection.Open();
            if (Dno != "" && Dname == "")
            {
                String select_by_Dno = "select * from DEPARTMENT where Dno='" + Dno + "'";
                SqlCommand sqlCommand = new SqlCommand(select_by_Dno, sqlConnection);
                SqlDataReader sqlDataReader = sqlCommand.ExecuteReader();
                BindingSource bindingSource = new BindingSource();
                bindingSource.DataSource = sqlDataReader;
                departmenttable.DataSource = bindingSource;
            }
            else if (Dname != "" && Dno == "")
            {
                String select_by_Dname = "select * from DEPARTMENT where Dname='" + Dname + "'";
                SqlCommand sqlCommand1 = new SqlCommand(select_by_Dname, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                departmenttable.DataSource = bindingSource1;
            }
            else if (Dname == "" && Dno == "")
            {
                String select_by_Dname = "select * from DEPARTMENT ";
                SqlCommand sqlCommand1 = new SqlCommand(select_by_Dname, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                departmenttable.DataSource = bindingSource1;
            }
            else
            {
                MessageBox.Show("输入有误，请检查!", "警告");
            }

            sqlConnection.Close();
        }

        private void Button2_Click(object sender, EventArgs e) //修改
        {
            String Dno = textBox1.Text.Trim();
            String Dname = textBox2.Text.Trim();
            String Dpop = textBox3.Text.Trim();

            SqlConnection con = new SqlConnection("Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117");
            try
            {
                con.Open();
                string insertStr = "UPDATE DEPARTMENT SET Dname = '" + Dname + "',Dpop='" + Dpop + "' WHERE Dno = '" +
                                   Dno + "'";
                SqlCommand cmd = new SqlCommand(insertStr, con);
                cmd.ExecuteNonQuery();
            }
            catch
            {
                MessageBox.Show("输入数据违反要求!", "警告");
            }
            finally
            {
                con.Dispose();
            }

            this.dEPARTMENTTableAdapter.Fill(this.pMSDataSet4.DEPARTMENT);
        }

        private void Button3_Click(object sender, EventArgs e) //增加
        {
            String Dno = textBox1.Text.Trim();
            String Dname = textBox2.Text.Trim();
            String Dpop = textBox3.Text.Trim();

            SqlConnection con = new SqlConnection("Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117");
            try
            {
                if (Dno == "" || Dname == "" || Dpop == "")
                {
                    MessageBox.Show("请将信息填写完整!", "警告");
                }
                else
                {
                    con.Open();
                    string insertStr = "INSERT INTO  DEPARTMENT (Dno,Dname,Dpop)    " +
                                       "VALUES ('" + Dno + "','" + Dname + "','" + Dpop + "')";
                    SqlCommand cmd = new SqlCommand(insertStr, con);
                    cmd.ExecuteNonQuery();
                }
            }
            catch
            {
                MessageBox.Show("输入数据违反要求!", "警告");
            }
            finally
            {
                con.Dispose();
            }

            this.dEPARTMENTTableAdapter.Fill(this.pMSDataSet4.DEPARTMENT);
        }

        private void Button8_Click(object sender, EventArgs e) //查询
        {
            String RATEID = textBox6.Text.Trim();

            String conn = "Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117";

            SqlConnection sqlConnection = new SqlConnection(conn); //实例化连接对象
            try
            {
                sqlConnection.Open();
                String select_by_RATEID = "select * from RATE where RATEID='" + RATEID + "'";
                SqlCommand sqlCommand = new SqlCommand(select_by_RATEID, sqlConnection);
                SqlDataReader sqlDataReader = sqlCommand.ExecuteReader();
                BindingSource bindingSource = new BindingSource();
                bindingSource.DataSource = sqlDataReader;
                dataGridView1.DataSource = bindingSource;
            }
            catch
            {
                MessageBox.Show("查询有误，请检查!", "警告");
            }
            finally
            {
                sqlConnection.Close();
            }
        }

        private void Button7_Click(object sender, EventArgs e) //修改
        {
            String RATEID = textBox6.Text.Trim();
            String INSURANCE = textBox5.Text.Trim();
            String TAX = textBox4.Text.Trim();
            String MINpaytax = textBox7.Text.Trim();

            SqlConnection con = new SqlConnection("Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117");
            try
            {
                con.Open();
                string insertStr = "UPDATE RATE SET INSURANCE = '" + INSURANCE + "',TAX='" + TAX + "',MINpaytax='" +
                                   MINpaytax + "' WHERE RATEID = '" + RATEID + "'";
                SqlCommand cmd = new SqlCommand(insertStr, con);
                cmd.ExecuteNonQuery();
            }
            catch
            {
                MessageBox.Show("输入数据违反要求!", "警告");
            }
            finally
            {
                con.Dispose();
            }

            this.rATETableAdapter.Fill(this.pMSDataSet10.RATE);
        }

        private void Button4_Click(object sender, EventArgs e) //增加
        {
            String RATEID = textBox6.Text.Trim();
            String INSURANCE = textBox5.Text.Trim();
            String TAX = textBox4.Text.Trim();
            String MINpaytax = textBox7.Text.Trim();

            SqlConnection con = new SqlConnection("Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117");
            try
            {
                if (RATEID == "" || INSURANCE == "" || TAX == "" || MINpaytax == "")
                {
                    MessageBox.Show("请将信息填写完整!", "警告");
                }
                else
                {
                    con.Open();
                    string insertStr = "INSERT INTO  RATE (RATEID,INSURANCE,TAX,MINpaytax)    " +
                                       "VALUES ('" + RATEID + "','" + INSURANCE + "','" + TAX + "','" + MINpaytax +
                                       "')";
                    SqlCommand cmd = new SqlCommand(insertStr, con);
                    cmd.ExecuteNonQuery();
                }
            }
            catch
            {
                MessageBox.Show("输入数据违反要求!");
            }
            finally
            {
                con.Dispose();
            }

            this.rATETableAdapter.Fill(this.pMSDataSet10.RATE);
        }

        private void Button9_Click(object sender, EventArgs e) //删除
        {
            SqlConnection con = new SqlConnection("Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117");
            try
            {
                con.Open();
                string select_Dno = departmenttable.SelectedRows[0].Cells[0].Value.ToString(); //选择的当前行第一列的值，也就是ID
                string delete_by_Dno = "delete from DEPARTMENT where Dno=" + select_Dno; //sql删除语句
                SqlCommand cmd = new SqlCommand(delete_by_Dno, con);
                cmd.ExecuteNonQuery();
            }
            catch
            {
                MessageBox.Show("请正确选择行!");
            }
            finally
            {
                con.Dispose();
            }

            this.dEPARTMENTTableAdapter.Fill(this.pMSDataSet4.DEPARTMENT);
        }
    }
}