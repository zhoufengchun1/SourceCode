using System;
using System.Windows.Forms;

namespace OCS
{
    public partial class 主界面 : Form
    {
        private User user;
        private 好友与聊天 chatForm;
        private 个人管理 squareForm;
        private 添加好友 addFriendsForm;
        private Form[] _forms = new Form[3];
        private TabPage[] tabPages = new TabPage[3];
        private int i;


        public 主界面(User user)
        {
            InitializeComponent();
            this.user = user;
            this.MaximizeBox = false;
            this.StartPosition = FormStartPosition.CenterScreen;
            DrawForm();
            _forms[0].Show();
        }

        private void 主界面_Load(object sender, EventArgs e)
        {
        }

        private void 主界面_FormClosing(object sender, FormClosingEventArgs e)
        {
           
                DialogResult dialog =
                MessageBox.Show("是否退出程序？", "退出程序", MessageBoxButtons.YesNo, MessageBoxIcon.Exclamation);
            if (dialog == DialogResult.Yes)
                Environment.Exit(0);
            else
            {
                e.Cancel = true;
            }
        }


        private void DrawForm()
        {
            chatForm = new 好友与聊天(user);
            squareForm = new 个人管理(user);
            addFriendsForm = new 添加好友(user);
            _forms[0] = chatForm;
            _forms[1] = squareForm;
            _forms[2] = addFriendsForm;
            tabPages[0] = tabPage1;
            tabPages[1] = tabPage2;
            tabPages[2] = tabPage3;
            for (int i = 0; i < _forms.Length; i++)
            {
                _forms[i].FormBorderStyle = FormBorderStyle.None;
                _forms[i].TopLevel = false;
                _forms[i].ControlBox = false;
                _forms[i].Dock = DockStyle.Fill;
                _forms[i].StartPosition = FormStartPosition.CenterParent;
                _forms[i].Parent = tabPages[i];
            }
        }
        


        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {
            foreach (Form f in _forms)
            {
                if (!tabControl1.SelectedIndex.Equals(i))
                {
                    f.Show();
                }else
                    f.Hide();
            }
            Console.Write(i);
            i = tabControl1.SelectedIndex;     
        }
    }
}