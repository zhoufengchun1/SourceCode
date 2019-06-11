using System.ComponentModel;

namespace OCS
{
    partial class 移动分组
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.labelUserId = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.userGroup = new System.Windows.Forms.Label();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.button1 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            this.label1.Location = new System.Drawing.Point(1937, 1494);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(78, 18);
            this.label1.TabIndex = 0;
            this.label1.Text = "label1";
            this.label2.Font = new System.Drawing.Font("Microsoft YaHei UI", 15F, System.Drawing.FontStyle.Regular,
                System.Drawing.GraphicsUnit.Point, ((byte) (134)));
            this.label2.Location = new System.Drawing.Point(28, 23);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(32, 32);
            this.label2.TabIndex = 1;
            this.label2.Text = "将";
            this.labelUserId.Font = new System.Drawing.Font("Microsoft YaHei UI", 15F, System.Drawing.FontStyle.Regular,
                System.Drawing.GraphicsUnit.Point, ((byte) (134)));
            this.labelUserId.Location = new System.Drawing.Point(58, 23);
            this.labelUserId.Name = "labelUserId";
            this.labelUserId.Size = new System.Drawing.Size(129, 32);
            this.labelUserId.TabIndex = 2;
            this.labelUserId.Text = "userId";
            this.labelUserId.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.label4.Font = new System.Drawing.Font("Microsoft YaHei UI", 15F, System.Drawing.FontStyle.Regular,
                System.Drawing.GraphicsUnit.Point, ((byte) (134)));
            this.label4.Location = new System.Drawing.Point(193, 23);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(31, 32);
            this.label4.TabIndex = 3;
            this.label4.Text = "从";
            this.label5.Font = new System.Drawing.Font("Microsoft YaHei UI", 15F, System.Drawing.FontStyle.Regular,
                System.Drawing.GraphicsUnit.Point, ((byte) (134)));
            this.label5.Location = new System.Drawing.Point(357, 23);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(86, 32);
            this.label5.TabIndex = 4;
            this.label5.Text = "移动到";
            this.userGroup.Font = new System.Drawing.Font("Microsoft YaHei UI", 15F, System.Drawing.FontStyle.Regular,
                System.Drawing.GraphicsUnit.Point, ((byte) (134)));
            this.userGroup.Location = new System.Drawing.Point(230, 23);
            this.userGroup.Name = "userGroup";
            this.userGroup.Size = new System.Drawing.Size(121, 32);
            this.userGroup.TabIndex = 5;
            this.userGroup.Text = "分组";
            this.userGroup.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.comboBox1.Font = new System.Drawing.Font("Microsoft YaHei UI", 15F);
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Location = new System.Drawing.Point(508, 23);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(127, 35);
            this.comboBox1.TabIndex = 6;
            this.button1.Font = new System.Drawing.Font("宋体", 15F, System.Drawing.FontStyle.Bold,
                System.Drawing.GraphicsUnit.Point, ((byte) (134)));
            this.button1.Location = new System.Drawing.Point(194, 72);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(135, 35);
            this.button1.TabIndex = 7;
            this.button1.Text = "确认";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(655, 119);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.userGroup);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.labelUserId);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Name = "移动分组";
            this.Text = "移动分组";
            this.Load += new System.EventHandler(this.移动分组_Load);
            this.ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label labelUserId;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label userGroup;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.Button button1;
    }
}