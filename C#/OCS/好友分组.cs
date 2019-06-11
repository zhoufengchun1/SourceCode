using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace OCS
{
    public delegate void ChangeGroupHandler(string str);

    public partial class 好友分组 : Form
    {
        List<string> list = 好友与聊天.group;
        public event ChangeGroupHandler changeGroup;

        public 好友分组()
        {
            InitializeComponent();
            this.StartPosition = FormStartPosition.CenterScreen;
            this.MaximizeBox = false;
        }

        private void button2_Click(object sender, System.EventArgs e)
        {
            添加好友 form = (添加好友) Owner;
            if (comboBox1.Text == "")
            {
                changeGroup("默认");
            }else
            changeGroup(comboBox1.Text);
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Hide();
        }

        private void 好友分组_Load(object sender, EventArgs e)
        {
            foreach (string s in list)
            {
                comboBox1.Items.Add(s);
            }

            comboBox1.SelectedIndex = 0;
        }
    }
}