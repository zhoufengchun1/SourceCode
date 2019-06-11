using System;
using System.Windows.Forms;

namespace OCS
{
    public delegate void ChangeGroupName(string str);

    public partial class 修改组名 : Form
    {
        public event ChangeGroupHandler changeGroupName;

        public 修改组名(string str)
        {
            InitializeComponent();
            label2.Text = str;
            this.StartPosition = FormStartPosition.CenterScreen;
            this.MaximizeBox = false;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (textBox1.Text == "")
            {
                MessageBox.Show("请输入组名！", "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                changeGroupName(textBox1.Text);
            }

            this.Close();
        }

        public void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}