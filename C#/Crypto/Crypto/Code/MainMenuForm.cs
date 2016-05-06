using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Crypto
{
    public partial class MainMenuForm : Form
    {
        public MainMenuForm()
        {
            InitializeComponent();
        }

        private void buttonAES_MouseClick(object sender, MouseEventArgs e)
        {
            var form = new AES_Encrypter.AESForm();
            form.Show();
        }

        private void buttonRC4_MouseClick(object sender, MouseEventArgs e)
        {
            var form = new RC4_Encrypter.RC4Form();
            form.Show();
        }
    }
}
