using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace RC4_Encrypter
{
        
    public class RC4Logic
    {
        private static int i = 0; //an index which is part of the internal state
        private static int j = 0; //an index which is part of the internal state
        private static byte[] s = new byte[256]; //permutation array for all 256 possible bytes

        /* 
         * Encrypt the string plaintext with the hex key specified by key
         */
        public string Encrypt(string plaintext, string key)
        {
            byte[] plainTextBytes = System.Text.Encoding.UTF8.GetBytes(plaintext); //string to byte[]
            byte[] keyBytes = HexStringToByteArray(key);
            KSA(keyBytes);
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < plainTextBytes.Length; k++)
            {
                sb.Append((plainTextBytes[k] ^ GetNextByte()).ToString("X2"));
            }
            return sb.ToString();
        }

        /* 
         * Decrypt the string cipherText with the hex key specified by key
         */
        public string Decrypt(string cipherText, string key)
        {
            byte[] cipherTextBytes = HexStringToByteArray(cipherText);
            byte[] keyBytes = HexStringToByteArray(key);
            KSA(keyBytes);
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < cipherTextBytes.Length; k++)
            {
                sb.Append((cipherTextBytes[k] ^ GetNextByte()).ToString("X2"));
            }
            return sb.ToString();
        }

        /*
         * Key Scheduling Algorithm which initializes the permutation array s
         */
        private void KSA(byte[] key)
        {
            i = 0;
            j = 0;
            for (int ii = 0; ii <= 255; ii++)
                s[ii] = (byte)ii;
                int jj = 0;
                for (int ii = 0; ii <= 255; ii++)
                {
                    jj = (jj + s[ii] + key[ii % key.Length]) % 256;
                    byte temp = s[ii]; //swap s[ii] and s[jj]
                    s[ii] = s[jj];
                    s[jj] = temp;
                }
        }

        /*
         * Get the next byte of the cipherstream
         */
        private byte GetNextByte()
        {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            byte temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            return s[(s[i] + s[j]) % 256];
        }

        /*
         * Convert a string of hexadecimal characters to a byte array
         */
        private byte[] HexStringToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length)
                    .Where(x => x % 2 == 0)
                    .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                    .ToArray();
        }
    }
}

