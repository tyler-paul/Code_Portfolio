using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AES_Encrypter
{
    public class AESLogic
    {
        private const int NR = 10; //number of rounds
        private const int NB = 16; //number of bytes for a round key 16 bytes = 128 bit key
        private const int NK = 4; //number of words in a round key

        private static byte[] s = new byte[256] 
        {
              //0     1     2     3     4     5     6     7     8     9     A     B     C     D     E     F
                0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76, //0
                0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0, //1
                0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15, //2
                0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75, //3
                0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84, //4
                0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF, //5
                0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8, //6
                0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2, //7
                0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73, //8
                0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB, //9
                0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79, //A
                0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08, //B
                0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A, //C
                0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E, //D
                0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF, //E
                0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16  //F
        };
        private static byte[] inv_s = new byte[256]
        {
                0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
                0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
                0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
                0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
                0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
                0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
                0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
                0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
                0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
                0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
                0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
                0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
                0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
                0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
                0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
                0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D
        };
        private static byte[] rcon = new byte[256]
        {
                0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
                0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
                0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
                0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
                0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
                0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
                0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
                0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
                0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
                0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
                0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
                0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
                0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
                0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
                0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
                0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
        };

        /*
         * Encrypt plaintextString using cipherblock chaining with a specified key and iv
         */
        public string CBC(string plaintextString, byte[] iv, byte[] key)
        {
            byte[] plaintext = System.Text.Encoding.UTF8.GetBytes(plaintextString.TrimEnd('\r', '\n'));
            key = changeOrder(key);
            iv = changeOrder(iv);

            StringBuilder stringBuilder = new StringBuilder();
            byte[] plainTextBlock = new byte[16];
            byte[] lastCipherTextBlock = iv;

            for (int i = 0; i < (plaintext.Length - 1) / 16 + 1; i++)
            {
                getNextBlock(plaintext, i, plainTextBlock);
                lastCipherTextBlock = Encrypt(EORState(plainTextBlock, lastCipherTextBlock), key);
                AddBytesToString(lastCipherTextBlock, stringBuilder);
            }
            return stringBuilder.ToString();
        }

        /*
         * Decrypt ciphertextString using cipherblock chaining with a specified key and iv
         */
        public string InvCBC(string ciphertextString, byte[] iv, byte[] key)
        {
            byte[] ciphertext = StringToByteArray(ciphertextString.TrimEnd('\r', '\n'));
            key = changeOrder(key);
            iv = changeOrder(iv);

            StringBuilder stringBuilder = new StringBuilder();
            byte[] cipherTextBlock = new byte[16];
            byte[] previous = iv;
            byte[] temp = new byte[16];
            for (int i = 0; i < (ciphertext.Length - 1) / 16 + 1; i++)
            {
                getNextBlock(ciphertext, i, cipherTextBlock);
                AddBytesToString(EORState(Decrypt(cipherTextBlock, key), previous), stringBuilder);
                Array.Copy(cipherTextBlock, previous, 16);
            }
            return stringBuilder.ToString();
        }

        private void getNextBlock(byte[] text, int index, byte[] textBlock)
        {
            if (index == text.Length / 16)
            {
                Array.Clear(textBlock, 0, 16);
                for (int i = 0; i < text.Length % 16; i++)
                    textBlock[4 * (i % 4) + i / 4] = text[index * 16 + i];
            }
            else
            {
                for (int i = 0; i < 16; i++)
                    textBlock[4 * (i % 4) + i / 4] = text[index * 16 + i];
            }
        }

        private byte[] Encrypt(byte[] plaintextBlock, byte[] key)
        {
            byte[] state = plaintextBlock;

            //expand the key into NR + 1 round keys
            byte[][] expandedKey = KeyExpansion(key);

            for (int i = 0; i < NR - 1; i++)
            {
                state = EORState(state, GetRoundKey(expandedKey, i));
                Round(state);
            }

            state = EORState(state, GetRoundKey(expandedKey, NR - 1));
            FinalRound(state);

            state = EORState(state, GetRoundKey(expandedKey, NR));
            //state = changeOrder(state);

            return state;
        }

        private byte[] Decrypt(byte[] ciphertextBlock, byte[] key)
        {
            byte[] state = ciphertextBlock;
            //expand the key into NR + 1 round keys
            byte[][] expandedKey = KeyExpansion(key);

            state = EORState(state, GetRoundKey(expandedKey, NR));
            InvFinalRound(state);

            for (int i = NR - 1; i > 0; i--)
            {
                state = EORState(state, GetRoundKey(expandedKey, i));
                InvRound(state);
            }

            state = EORState(state, GetRoundKey(expandedKey, 0));

            return state;
        }

        private void Round(byte[] a)
        {
            SubBytes(a);
            ShiftRows(a);
            MixColumns(a);
        }

        private void InvRound(byte[] a)
        {
            InvMixColumns(a);
            InvShiftRows(a);
            InvSubBytes(a);
        }

        private void FinalRound(byte[] a)
        {
            SubBytes(a);
            ShiftRows(a);
        }

        private void InvFinalRound(byte[] a)
        {
            InvShiftRows(a);
            InvSubBytes(a);
        }

        private void SubBytes(byte[] a)
        {
            for (int i = 0; i < 16; i++)
                a[i] = s[a[i]];
        }

        private void InvSubBytes(byte[] a)
        {
            for (int i = 0; i < 16; i++)
                a[i] = inv_s[a[i]];
        }

        private void ShiftRows(byte[] a)
        {
            //shift second row one entry to the left: a b c d -> b c d a
            byte temp = a[4];
            a[4] = a[5]; a[5] = a[6]; a[6] = a[7]; a[7] = temp;
            //shift third row two entries to the left: a b c d -> c d a b
            temp = a[8];
            byte temp2 = a[9];
            a[8] = a[10]; a[9] = a[11]; a[10] = temp; a[11] = temp2;
            //shift fourth row three entries to the left: a b c d -> d a b c
            temp = a[12];
            temp2 = a[13];
            byte temp3 = a[14];
            a[12] = a[15]; a[13] = temp; a[14] = temp2; a[15] = temp3;
        }

        private void InvShiftRows(byte[] a)
        {
            //shift second row one entry to the right: a b c d -> d a b c
            byte temp = a[7];
            a[7] = a[6]; a[6] = a[5]; a[5] = a[4]; a[4] = temp;
            //shift third row two entries to the right: a b c d -> c d a b
            temp = a[8];
            byte temp2 = a[9];
            a[8] = a[10]; a[9] = a[11]; a[10] = temp; a[11] = temp2;
            //shift fourth row three entries to the right: a b c d -> b c d a
            temp = a[15];
            temp2 = a[14];
            byte temp3 = a[13];
            a[15] = a[12]; a[14] = temp; a[13] = temp2; a[12] = temp3;
        }

        private Byte GMul(Byte a, Byte b)
        { // Galois Field (256) Multiplication of two Bytes
            Byte p = 0;
            Byte counter;
            Byte hi_bit_set;
            for (counter = 0; counter < 8; counter++)
            {
                if ((b & 1) != 0)
                {
                    p ^= a;
                }
                hi_bit_set = (Byte)(a & 0x80);
                a <<= 1;
                if (hi_bit_set != 0)
                {
                    a ^= 0x1b; /* x^8 + x^4 + x^3 + x + 1 */
                }
                b >>= 1;
            }
            return p;
        }

        private void MixColumns(byte[] s)
        { // 's' is the main State matrix, 'ss' is a temp matrix of the same dimensions as 's'.
            byte[] ss = new byte[16];
            Array.Clear(ss, 0, ss.Length);

            for (int c = 0; c < 4; c++)
            {
                ss[c] = (Byte)(GMul(0x02, s[c]) ^ GMul(0x03, s[4 + c]) ^ s[8 + c] ^ s[12 + c]);
                ss[4 + c] = (Byte)(s[c] ^ GMul(0x02, s[4 + c]) ^ GMul(0x03, s[8 + c]) ^ s[12 + c]);
                ss[8 + c] = (Byte)(s[c] ^ s[4 + c] ^ GMul(0x02, s[8 + c]) ^ GMul(0x03, s[12 + c]));
                ss[12 + c] = (Byte)(GMul(0x03, s[c]) ^ s[4 + c] ^ s[8 + c] ^ GMul(0x02, s[12 + c]));
            }

            ss.CopyTo(s, 0);
        }

        private void InvMixColumns(byte[] s)
        { 
            byte[] ss = new byte[16];
            Array.Clear(ss, 0, ss.Length);

            for (int c = 0; c < 4; c++)
            {
                ss[c] = (Byte)(GMul(0x0E, s[c]) ^ GMul(0x0B, s[4 + c]) ^ GMul(0x0D, s[8 + c]) ^ GMul(0x09, s[12 + c]));
                ss[4 + c] = (Byte)(GMul(0x09, s[c]) ^ GMul(0x0E, s[4 + c]) ^ GMul(0x0B, s[8 + c]) ^ GMul(0x0D, s[12 + c]));
                ss[8 + c] = (Byte)(GMul(0x0D, s[c]) ^ GMul(0x09, s[4 + c]) ^ GMul(0x0E, s[8 + c]) ^ GMul(0x0B, s[12 + c]));
                ss[12 + c] = (Byte)(GMul(0x0B, s[c]) ^ GMul(0x0D, s[4 + c]) ^ GMul(0x09, s[8 + c]) ^ GMul(0x0E, s[12 + c]));
            }

            ss.CopyTo(s, 0);
        }

        private void PrintBytes(byte[] a)
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                    Console.Write("{0:X2} ", a[4 * i + j]);
                Console.WriteLine();
            }
            Console.WriteLine();
        }

        private void AddBytesToString(byte[] a, StringBuilder stringBuilder)
        {
            stringBuilder.Append(BitConverter.ToString(changeOrder(a)).Replace("-", string.Empty));
        }

        private byte[][] KeyExpansion(byte[] key)
        {
            byte[][] w = new byte[NK * (NR + 1)][];

            for (int i = 0; i < NK; i++)
            {
                w[i] = new byte[4] { key[i], key[4 + i], key[8 + i], key[12 + i] };
            }

            for (int i = NK; i < NK * (NR + 1); i++)
            {
                byte[] temp = w[i - 1];
                if (i % NK == 0)
                    temp = EORWord(SubByte(RotByte(temp)), RCon(i / NK));
                w[i] = EORWord(w[i - NK], temp);
            }

            return w;
        }

        private byte[] GetRoundKey(byte[][] expandedKey, int num)
        {
            num = 4 * num;
            return new byte[16] { expandedKey[num][0], expandedKey[num + 1][0], expandedKey[num + 2][0], expandedKey[num + 3][0],
                                expandedKey[num ][1], expandedKey[num + 1][1], expandedKey[num + 2][1], expandedKey[num + 3][1],
                                expandedKey[num][2], expandedKey[num + 1][2], expandedKey[num + 2][2], expandedKey[num + 3][2],
                                expandedKey[num ][3], expandedKey[num + 1][3], expandedKey[num + 2][3], expandedKey[num + 3][3]
            };
        }

        private byte[] RotByte(byte[] word)
        {
            return new byte[4] { word[1], word[2], word[3], word[0] };
        }

        private byte[] SubByte(byte[] word)
        {
            return new byte[4] { s[word[0]], s[word[1]], s[word[2]], s[word[3]] };
        }

        private byte[] EORWord(byte[] word1, byte[] word2)
        {
            return new byte[4] { (byte)(word1[0] ^ word2[0]), (byte)(word1[1] ^ word2[1]), (byte)(word1[2] ^ word2[2]), (byte)(word1[3] ^ word2[3]) };
        }

        private byte[] EORState(byte[] state, byte[] key)
        {
            byte[] newState = new byte[16];
            for (int i = 0; i < 16; i++)
            {
                newState[i] = (byte)(state[i] ^ key[i]);
            }
            return newState;
        }

        private byte[] RCon(int i)
        {
            return new byte[4] { rcon[i], 0, 0, 0 };
        }

        private byte[] changeOrder(byte[] a)
        {
            return new byte[16]
            {
                a[0], a[4], a[8], a[12],
                a[1], a[5], a[9], a[13],
                a[2], a[6], a[10], a[14],
                a[3], a[7], a[11], a[15]
            };
        }

        private static byte[] StringToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length)
                             .Where(x => x % 2 == 0)
                             .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                             .ToArray();
        }
    }
}
