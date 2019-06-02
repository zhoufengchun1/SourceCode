using System;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace PMS
{
    public partial class 出勤管理 : Form
    {
        public 出勤管理()
        {
            InitializeComponent();
        }
        // private object pMSDataSet1;

        public object ATTENDENCETableAdapter { get; private set; }

        private void 出勤管理_Load(object sender, EventArgs e)
        {
            // TODO: 这行代码将数据加载到表“pMSDataSet6.ATTENDENCE”中。您可以根据需要移动或删除它。
//            this.aTTENDENCETableAdapter1.Fill(this.pMSDataSet6.ATTENDENCE);
        }

        private void Button1_Click(object sender, EventArgs e) //写入
        {
            String Sno = textBox3.Text.Trim();
            String Overtime = textBox1.Text.Trim();
            String Absence = textBox2.Text.Trim();
            String Atime = textBox4.Text.Trim();
            SqlConnection con = new SqlConnection("Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117");

            try
            {
                if (Sno == "" || Overtime == "" || Absence == "" || Atime == "")
                {
                    MessageBox.Show("请将信息填写完整!", "警告");
                }
                else
                {
                    con.Open();
                    string insertStr = "INSERT INTO  ATTENDENCE (Sno,OVERTIME,ABSENCE,ATIME) " +
                                       "VALUES ('" + Sno + "','" + Overtime + "','" + Absence + "','" + Atime + "')";
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

            this.aTTENDENCETableAdapter1.Fill(this.pMSDataSet6.ATTENDENCE);
        }

        private void TextBox3_TextChanged(object sender, EventArgs e)
        {
        }

        private void Button2_Click(object sender, EventArgs e) //删除
        {
            SqlConnection con = new SqlConnection("Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117");
            try
            {
                //问题 删除了所有工号相同的  怎么实现选中哪行删哪行
                con.Open();
                string select_id = stafftable.SelectedRows[0].Cells[1].Value.ToString(); //选择的当前行第一列的值
                string delete_by_id = "delete from ATTENDENCE where Sno=" + select_id; //sql删除语句
                SqlCommand cmd = new SqlCommand(delete_by_id, con);
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

            this.aTTENDENCETableAdapter1.Fill(this.pMSDataSet6.ATTENDENCE);
        }

        private void Button3_Click(object sender, EventArgs e) //查询
        {
            String Sno = textBox3.Text.Trim();
            String OVERTIME = textBox1.Text.Trim();
            String ABSENCE = textBox2.Text.Trim();
            String ATIME = textBox4.Text.Trim();


            String conn = "Data Source=.;Initial Catalog=PMS;User ID=sa;Password=1641117";

            SqlConnection sqlConnection = new SqlConnection(conn); //实例化连接对象
            sqlConnection.Open();

            if (Sno != "" && ATIME == "")
            {
                String select_by_Sno = "select * from ATTENDENCE where Sno='" + Sno + "'";
                SqlCommand sqlCommand = new SqlCommand(select_by_Sno, sqlConnection);
                SqlDataReader sqlDataReader = sqlCommand.ExecuteReader();
                BindingSource bindingSource = new BindingSource();
                bindingSource.DataSource = sqlDataReader;
                stafftable.DataSource = bindingSource;
            }
            else if (Sno == "" && ATIME != "")
            {
                String select_by_ATIME = "select * from ATTENDENCE where ATIME='" + ATIME + "'";
                SqlCommand sqlCommand1 = new SqlCommand(select_by_ATIME, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                stafftable.DataSource = bindingSource1;
            }
            else if (Sno == "" && ATIME == "")
            {
                String select = "select * from ATTENDENCE ";
                SqlCommand sqlCommand1 = new SqlCommand(select, sqlConnection);
                SqlDataReader sqlDataReader1 = sqlCommand1.ExecuteReader();
                BindingSource bindingSource1 = new BindingSource();
                bindingSource1.DataSource = sqlDataReader1;
                stafftable.DataSource = bindingSource1;
            }
            else
            {
                MessageBox.Show("输入有误，请检查!", "警告");
            }

            sqlConnection.Close();
        }
    }
}