using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace PMS
{
    public partial class 员工查询 : Form
    {
        private List<People> list;

        public 员工查询()
        {
            InitializeComponent();
            list = new List<People>();
            this.StartPosition = FormStartPosition.CenterScreen;
        }


        private void Button6_Click(object sender, EventArgs e) //工资查询
        {
            list.Clear();
            list.Add(new People("Sno", textBox10.Text.Trim()));
            list.Add(new People("Sname", textBox9.Text.Trim()));
            list.Add(new People("Deptno", textBox11.Text));
            list.Add(new People("Paytime", textBox11.Text.Trim()));
            new Query("select * from temp where ", list, salarytable).ExecuteQuery();
        }

        private void Button2_Click(object sender, EventArgs e) //代扣查询
        {
            list.Clear();
            list.Add(new People("WITHHOLDID", textBox12.Text.Trim()));
            list.Add(new People("Sno", textBox5.Text.Trim()));
            list.Add(new People("RID", comboBox2.Text));
            list.Add(new People("WITHHOLDTIME", textBox8.Text.Trim()));
            new Query("select * from WITHHOLD where ", list, withholdtable).ExecuteQuery();
        }

        private void Button3_Click(object sender, EventArgs e) //出勤查询
        {
            list.Clear();
            list.Add(new People("Sno", textBox3.Text.Trim()));
            list.Add(new People("OVERTIME", textBox1.Text.Trim()));
            list.Add(new People("ABSENCE", textBox2.Text));
            list.Add(new People("ATIME", textBox4.Text.Trim()));
            new Query("select * from ATTENDENCE where ", list, attendencetable).ExecuteQuery();
        }

        private void Button7_Click(object sender, EventArgs e) //同事查询
        {
            list.Clear();
            list.Add(new People("Sno", textBox17.Text.Trim()));
            list.Add(new People("Sname", textBox16.Text.Trim()));
            list.Add(new People("Ssex", comboBox3.Text));
            list.Add(new People("Sbirth", textBox13.Text.Trim()));
            list.Add(new People("Deptno", comboBox1.Text));
            list.Add(new People("Slevel", textBox15.Text.Trim()));
            list.Add(new People("Stel", textBox6.Text.Trim()));
            list.Add(new People("JoinTime", textBox14.Text.Trim()));
            new Query("select * from STAFF where ", list, stafftable).ExecuteQuery();
        }

        private void 员工查询_FormClosing(object sender, FormClosingEventArgs e)
        {
            DialogResult dialog = MessageBox.Show("是否退出程序？", "退出程序", MessageBoxButtons.YesNo, MessageBoxIcon.Exclamation);
            if (dialog == DialogResult.Yes)
                Environment.Exit(0);
            else
            {
                e.Cancel = true;
            }
        }
    }
}