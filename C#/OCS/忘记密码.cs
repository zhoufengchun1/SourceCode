using System;
using System.Text.RegularExpressions;
using System.Windows.Forms;

namespace OCS
{
    public partial class 忘记密码 : Form
    {
        private Form form;
        private string userName;
        
        public 忘记密码(Form form)
        {
            InitializeComponent();
            this.form = form;
            this.MaximizeBox = false;
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }


        private void textBox3_KeyPress(object sender, KeyPressEventArgs e)
        {
            if ((e.KeyChar != '\b') &&(!Char.IsLetter(e.KeyChar)) && (!char.IsDigit(e.KeyChar)))
            {
                e.Handled = true;
            }
        }

        private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
        {
            Regex rg = new Regex("^[\u4e00-\u9fa5]$"); //正则表达式
            if (rg.IsMatch(e.KeyChar.ToString()) && e.KeyChar != '\b') //'\b'是退格键
            {
                e.Handled = true;
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            this.Hide();
            form.Show();
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (!Regex.IsMatch(textBox2.Text,
               "^\\s*([A-Za-z0-9_-]+(\\.\\w+)*@(\\w+\\.)+\\w{2,5})\\s*$"))
            {
                MessageBox.Show("请输入正确的邮箱格式！\n 邮箱是找回密码的唯一凭据，请正确输入。", "注册失败", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
            }else
            {
                userName = textBox1.Text;
                
            }
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox1.Checked)
            {
                //复选框被勾选，明文显示
                textBox2.PasswordChar = new char();
            }
            else
            {
                //复选框被取消勾选，密文显示
                textBox2.PasswordChar = '*';
            }
        }
    }
}