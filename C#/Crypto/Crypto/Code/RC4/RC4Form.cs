﻿using System;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;

namespace RC4_Encrypter
{
    class RC4Form : Form
    {
        private Button buttonEncrypt;
        private Label label1;
        private Label label2;
        private Label label4;
        private TextBox textBoxKey;
        private TextBox textBoxCipherText;
        private Button buttonDecrypt;
        private TextBox textBoxPlaintext;
        private RC4Logic rc4Logic;

        public RC4Form()
        {
            InitializeComponent();
            rc4Logic = new RC4Logic();
        }


        private void InitializeComponent()
        {
            this.buttonEncrypt = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.textBoxPlaintext = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.textBoxKey = new System.Windows.Forms.TextBox();
            this.textBoxCipherText = new System.Windows.Forms.TextBox();
            this.buttonDecrypt = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // buttonEncrypt
            // 
            this.buttonEncrypt.Location = new System.Drawing.Point(112, 346);
            this.buttonEncrypt.Name = "buttonEncrypt";
            this.buttonEncrypt.Size = new System.Drawing.Size(75, 23);
            this.buttonEncrypt.TabIndex = 0;
            this.buttonEncrypt.Text = "Encrypt";
            this.buttonEncrypt.UseVisualStyleBackColor = true;
            this.buttonEncrypt.Click += new System.EventHandler(this.buttonEncrypt_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(26, 36);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(50, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "Plaintext:";
            this.label1.Click += new System.EventHandler(this.label1_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(48, 170);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(28, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Key:";
            this.label2.Click += new System.EventHandler(this.label2_Click);
            // 
            // textBoxPlaintext
            // 
            this.textBoxPlaintext.Location = new System.Drawing.Point(90, 33);
            this.textBoxPlaintext.Multiline = true;
            this.textBoxPlaintext.Name = "textBoxPlaintext";
            this.textBoxPlaintext.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.textBoxPlaintext.Size = new System.Drawing.Size(318, 75);
            this.textBoxPlaintext.TabIndex = 4;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(15, 253);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(57, 13);
            this.label4.TabIndex = 5;
            this.label4.Text = "Ciphertext:";
            this.label4.Click += new System.EventHandler(this.label4_Click);
            // 
            // textBoxKey
            // 
            this.textBoxKey.Location = new System.Drawing.Point(90, 170);
            this.textBoxKey.Name = "textBoxKey";
            this.textBoxKey.Size = new System.Drawing.Size(318, 20);
            this.textBoxKey.TabIndex = 6;
            this.textBoxKey.Text = "1267A3982269B71543D3891965E748F1";
            // 
            // textBoxCipherText
            // 
            this.textBoxCipherText.Location = new System.Drawing.Point(90, 253);
            this.textBoxCipherText.Multiline = true;
            this.textBoxCipherText.Name = "textBoxCipherText";
            this.textBoxCipherText.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.textBoxCipherText.Size = new System.Drawing.Size(318, 75);
            this.textBoxCipherText.TabIndex = 8;
            // 
            // buttonDecrypt
            // 
            this.buttonDecrypt.Location = new System.Drawing.Point(244, 346);
            this.buttonDecrypt.Name = "buttonDecrypt";
            this.buttonDecrypt.Size = new System.Drawing.Size(75, 23);
            this.buttonDecrypt.TabIndex = 9;
            this.buttonDecrypt.Text = "Decrypt";
            this.buttonDecrypt.UseVisualStyleBackColor = true;
            this.buttonDecrypt.Click += new System.EventHandler(this.buttonDecrypt_Click);
            // 
            // RC4Form
            // 
            this.ClientSize = new System.Drawing.Size(451, 463);
            this.Controls.Add(this.buttonDecrypt);
            this.Controls.Add(this.textBoxCipherText);
            this.Controls.Add(this.textBoxKey);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.textBoxPlaintext);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.buttonEncrypt);
            this.Name = "RC4Form";
            this.Text = "RC4 Encrypter";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void buttonEncrypt_Click(object sender, EventArgs e)
        {
            if (textBoxKey.Text.Length != 32 || !System.Text.RegularExpressions.Regex.IsMatch(textBoxKey.Text, @"\A\b[0-9a-fA-F]+\b\Z"))
            {
                MessageBox.Show("Key must be 32 hex characters.");
                return;
            }

            if (textBoxPlaintext.Text.Length == 0)
            {
                MessageBox.Show("Enter a plaintext string.");
                return;
            }

            textBoxCipherText.Text = rc4Logic.Encrypt(textBoxPlaintext.Text, textBoxKey.Text);
        }


        private void buttonDecrypt_Click(object sender, EventArgs e)
        {
            if (textBoxKey.Text.Length != 32 || !System.Text.RegularExpressions.Regex.IsMatch(textBoxKey.Text, @"\A\b[0-9a-fA-F]+\b\Z"))
            {
                MessageBox.Show("Key must be 32 hex characters.");
                return;
            }

            if (textBoxCipherText.Text.Length == 0)
            {
                MessageBox.Show("Enter a ciphertext string.");
                return;
            }
            textBoxPlaintext.Text = new string(System.Text.Encoding.UTF8.GetChars(
                StringToByteArray(rc4Logic.Decrypt(textBoxCipherText.Text, textBoxKey.Text))));

        }

        public static byte[] StringToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length)
                             .Where(x => x % 2 == 0)
                             .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                             .ToArray();
        }
    }
}
