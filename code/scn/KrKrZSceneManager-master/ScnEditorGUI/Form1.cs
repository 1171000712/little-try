using System;
using System.Windows.Forms;
using KrKrSceneManager;
using System.Collections.Generic;
using System.Threading;
using System.IO;
using System.Text;

namespace ScnEditorGUI {
    public partial class Form1 : Form {
        public Form1() {
            InitializeComponent();
            //MessageBox.Show("This GUI don't is a stable translation tool, this program is a Demo for my dll, the \"KrKrSceneManager.dll\" it's a opensoruce project to allow you make your program to edit any scn file (with sig PSB or MDF) or TJS2 Files (with sig TJS2100)\n\nHow to use:\n*Rigth Click in the window to open or save the file\n*Select the string in listbox and edit in the text box\n*Press enter to update the string\n\nThis program is unstable!");
        }
        public List<string> GetFiles(string path,string kzm)
        {
            string filename;
            List<string> FileList = new List<string>() { };
            DirectoryInfo dir = new System.IO.DirectoryInfo(path);
            FileInfo[] fil = dir.GetFiles();
            DirectoryInfo[] dii = dir.GetDirectories();
            foreach (FileInfo f in fil)
            {
                filename = f.FullName;
                if (filename.EndsWith(kzm))
                {
                    FileList.Add(filename);
                }
            }
            //获取子文件夹内的文件列表，递归遍历  
            foreach (DirectoryInfo d in dii)
            {
                List<string> a =GetFiles(d.FullName,kzm);
                foreach (String s in a) {
                    FileList.Add(s);
                }
            }
            return FileList;
        }

        private void openFileToolStripMenuItem_Click(object sender, EventArgs e) {
            FolderBrowserDialog dialog = new FolderBrowserDialog();
            dialog.Description = "Please select the scn folder";
            if (dialog.ShowDialog() == DialogResult.OK)
            {
                string path = dialog.SelectedPath;
                path = "G:\\e\\e3";
                string des = path+"(scn2ks)\\";
                if (Directory.Exists(des) == false)
                {
                    Directory.CreateDirectory(des);
                }

                List<string> fl = GetFiles(path,".scn");
                foreach (string s in fl) {
                    OpenFile(s,des);
                }
                MessageBox.Show("done.");
            }
        }
        bool ResourceMode = false;
        PSBResManager PRM = new PSBResManager();
        public PSBAnalyzer SCN;
        public  void OpenFile(string fname,string path) {  
                ResourceMode = false;
                listBox1.Items.Clear();
                SCN = new PSBAnalyzer(File.ReadAllBytes(fname));
                List<string> lines = new List<string>() { };
                foreach (string str in SCN.Import()) {
                    lines.Add(str);
                }
                string name = System.IO.Path.GetFileNameWithoutExtension(fname);
            StreamWriter sw = new StreamWriter(path + name + ".scn2ks", false, Encoding.Unicode);
            foreach (String str in lines) {
                sw.WriteLine(str);
            }
            sw.Close();
            
        }
        private void listBox1_SelectedIndexChanged(object sender, EventArgs e) {
            try {
                this.Text = "id: " + listBox1.SelectedIndex;
                textBox1.Text = listBox1.Items[listBox1.SelectedIndex].ToString();
            }
            catch { }
        }
        private void saveFileToolStripMenuItem_Click(object sender, EventArgs e) {

            System.Windows.Forms.FolderBrowserDialog dialog = new System.Windows.Forms.FolderBrowserDialog();
            dialog.Description = "Please select the scn2ks folder";
            System.Windows.Forms.FolderBrowserDialog dialog1 = new System.Windows.Forms.FolderBrowserDialog();
            dialog1.Description = "Please select the scn folder";
            if ((dialog1.ShowDialog() == System.Windows.Forms.DialogResult.OK) & (dialog.ShowDialog() == System.Windows.Forms.DialogResult.OK))
            {
                DialogResult dr = MessageBox.Show("Would you like to compress the script? (Recommended)\n\nDoes not work with old games.", "ScnEditorGUI", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                string path = dialog.SelectedPath;
                string path1 = dialog1.SelectedPath;
                path = "G:\\e\\e3(scn2ks)";
                path1 = "G:\\e\\e3";
                string des = path + "(ks2scn)\\";
                if (Directory.Exists(des) == false)
                {
                    Directory.CreateDirectory(des);
                }
                List<string> fl = GetFiles(path, ".scn2ks");
                List<string> fl1 = GetFiles(path1,".scn");
                foreach (string s in fl)
                {
                    string name = Path.GetFileNameWithoutExtension(s);
                    string ss = "";
                    foreach (string sss in fl1) {
                        if (Path.GetFileNameWithoutExtension(s).Equals(Path.GetFileNameWithoutExtension(sss))) {
                            ss = sss;
                            break;
                        }  
                    }
                    SCN = new PSBAnalyzer(File.ReadAllBytes(ss));
                    SCN.Import();
                    SCN.CompressPackget = dr == DialogResult.Yes;
                    List<String> ppp = new List<string>() { };
                    StreamReader sr = new StreamReader(s,Encoding.Unicode);
                    while (!sr.EndOfStream)
                    {
                        ppp.Add(sr.ReadLine());
                    }
                    sr.Close();
                    string[] Strings = ppp.ToArray();
                    foreach (string o in Strings) {
                        Console.WriteLine(o);
                    }
                    PSBStrMan.CompressionLevel = CompressionLevel.Z_BEST_COMPRESSION; //opitional
                    byte[] outfile = SCN.Export(Strings);
                    //System.IO.File.WriteAllBytes(des + name + ".scn", outfile);
                }
                MessageBox.Show("File Saved.");
            }
        }
        private void textBox1_KeyPress(object sender, KeyPressEventArgs e) {
            if (e.KeyChar == '\r' || e.KeyChar == '\n') {
                listBox1.Items[listBox1.SelectedIndex] = textBox1.Text;
            }
        }

        private void decompressImageToolStripMenuItem_Click(object sender, EventArgs e) {
            OpenFileDialog fd = new OpenFileDialog();
            fd.FileName = "";
            fd.Filter = "All Files | *.*";
            DialogResult dr = fd.ShowDialog();
            if (dr == DialogResult.OK) {
                byte[] input = System.IO.File.ReadAllBytes(fd.FileName);
                byte[] output = HuffmanTool.DecompressBitmap(input);
                string fname = System.IO.Path.GetFileNameWithoutExtension(fd.FileName);
                System.IO.File.WriteAllBytes(AppDomain.CurrentDomain.BaseDirectory + fname + "_decompressed.raw", output);
                MessageBox.Show("This Resouce is Writed to Dracu-Riot, you need discovery resolution and add a bmp header manually and the unknown resolution.\n\n(Probabbly use RGBQuad)", "ScnEditorGUI", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private void compressImageToolStripMenuItem_Click(object sender, EventArgs e) {
            OpenFileDialog fd = new OpenFileDialog();
            fd.FileName = "";
            fd.Filter = "All Files | *.*";
            DialogResult dr = fd.ShowDialog();
            if (dr == DialogResult.OK) {
                byte[] input = System.IO.File.ReadAllBytes(fd.FileName);
                byte[] output = HuffmanTool.CompressBitmap(input, true);
                string fname = System.IO.Path.GetFileNameWithoutExtension(fd.FileName);
                System.IO.File.WriteAllBytes(AppDomain.CurrentDomain.BaseDirectory + fname + "_compressed.res", output);
                MessageBox.Show("Compressed to Tool Dir.", "ScnEditorGUI", MessageBoxButtons.OK, MessageBoxIcon.Information);

            }
        }

        private void tryRecoveryToolStripMenuItem_Click(object sender, EventArgs e) {
            throw new NotImplementedException();
            /*
            OpenFileDialog ofd = new OpenFileDialog();
            ofd.Filter = "All PSB Files|*.psb;*.scn;*.pimg";
            DialogResult dr = ofd.ShowDialog();
            if (dr == DialogResult.OK) {
                try {
                    byte[] data = System.IO.File.ReadAllBytes(ofd.FileName);
                    data = PSBStrMan.TryRecovery(data);
                    System.IO.File.WriteAllBytes(ofd.FileName, data);
                    MessageBox.Show("Packget Offset's Updated", "ScnEditorGUI", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                catch (Exception ex) {
                    MessageBox.Show("Failed To Recovery:\n" + ex.Message, "ScnEditorGUI", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }*/
        }

        TJS2SManager TJSEditor;
        private void openToolStripMenuItem_Click(object sender, EventArgs e) {
            OpenFileDialog fd = new OpenFileDialog();
            fd.FileName = "";
            fd.Filter = "KiriKiri TJS Compiled Files | *.tjs";
            DialogResult dr = fd.ShowDialog();
            if (dr == DialogResult.OK) {
                byte[] Data = System.IO.File.ReadAllBytes(fd.FileName);
                TJSEditor = new TJS2SManager(Data);
                string[] Strings = TJSEditor.Import();
                listBox1.Items.Clear();
                foreach (string str in Strings)
                    listBox1.Items.Add(str);
            }
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e) {
            SaveFileDialog fd = new SaveFileDialog();
            fd.FileName = "";
            fd.Filter = "KiriKiri TJS Compiled Files | *.tjs";
            DialogResult dr = fd.ShowDialog();
            if (dr == DialogResult.OK) {
                string[] NewString = new string[listBox1.Items.Count];
                for (int i = 0; i < NewString.Length; i++)
                    NewString[i] = listBox1.Items[i].ToString();
                System.IO.File.WriteAllBytes(fd.FileName, TJSEditor.Export(NewString));
                MessageBox.Show("File Saved.");
            }
        }

        private void ClipboardSeekSample_Click(object sender, EventArgs e) {
            SeekUpdate.Enabled = ClipboardSeekSample.Checked;
        }

        string LastClip = string.Empty;
        private void SeekUpdate_Tick(object sender, EventArgs e) {
            string Clip = Clipboard.GetText();
            if (LastClip != Clip) {
                LastClip = Clip;
                Clip = SimplfyMatch(Clip);
                if (!string.IsNullOrWhiteSpace(Clip)) {
                    for (int i = 0; i < listBox1.Items.Count; i++) {
                        string Line = SimplfyMatch(listBox1.Items[i].ToString());
                        if (Line == Clip) {
                            SaveIfNeeded();
                            listBox1.SelectedIndex = i;
                            return;
                        }
                    }
                    for (int i = 0; i < listBox1.Items.Count; i++) {
                        string Line = SimplfyMatch(listBox1.Items[i].ToString());
                        if (Line.Contains(Clip)) {
                            SaveIfNeeded();
                            listBox1.SelectedIndex = i;
                            return;
                        }
                    }
                }
            }
        }

        private void SaveIfNeeded() {
            int Sel = listBox1.SelectedIndex;
            if (Sel < 0)
                return;
            if (textBox1.Text != listBox1.Items[Sel].ToString())
                listBox1.Items[Sel] = textBox1.Text;
        }

        /// <summary>
        /// Minify a String at the max.
        /// </summary>
        /// <param name="Str">The string to Minify</param>
        /// <returns>The Minified String</returns>
        internal static string SimplfyMatch(string Str) {
            string Output = TrimString(Str);
            for (int i = 0; i < MatchDel.Length; i++)
                Output = Output.Replace(MatchDel[i], "");
            return Output;
        }        

        /// <summary>
        /// Trim a String
        /// </summary>
        /// <param name="Txt">The String to Trim</param>
        /// <returns>The Result</returns>
        internal static string TrimString(string Input) {
            string Result = Input;
            Result = TrimStart(Result);
            Result = TrimEnd(Result);
            return Result;
        }

        /// <summary>
        /// Trim the Begin of the String
        /// </summary>
        /// <param name="Txt">The String to Trim</param>
        /// <returns>The Result</returns>
        internal static string TrimStart(string Txt) {
            string rst = Txt;
            foreach (string str in TrimContent) {
                if (string.IsNullOrEmpty(str))
                    continue;
                while (rst.StartsWith(str)) {
                    rst = rst.Substring(str.Length, rst.Length - str.Length);
                }
            }

            if (rst != Txt)
                rst = TrimStart(rst);

            return rst;
        }

        /// <summary>
        /// Trim the End of the String
        /// </summary>
        /// <param name="Txt">The String to Trim</param>
        /// <returns>The Result</returns>
        internal static string TrimEnd(string Txt) {
            string rst = Txt;
            foreach (string str in TrimContent) {
                if (string.IsNullOrEmpty(str))
                    continue;
                while (rst.EndsWith(str)) {
                    rst = rst.Substring(0, rst.Length - str.Length);
                }
            }            

            if (rst != Txt)
                rst = TrimEnd(rst);

            return rst;
        }
        static string[] MatchDel = new string[] {
            "\r", "\\r", "\n", "\\n", " ", "_r", "―", "-", "*", "♥", "①", "♪"
        };

        static string[] TrimContent = new string[] {
            " ", "'", "\"", "<", "(", "[", "“", "［", "《", "«",
            "「", "『", "【", "]", "”", "］", "》",
            "»", "」", "』", "】", ")", ">", "‘", "’", "〃", "″",
            "～", "~", "―", "-", "%K", "%LC", "♪", "%P", "%f;",
            "%fSourceHanSansCN-M;", "[―]"
        };

        private void decompressScriptToolStripMenuItem_Click(object sender, EventArgs e) {
            OpenFileDialog fd = new OpenFileDialog();
            fd.FileName = "All PSB Files|*.psb;*.scn;*.pimg";
            if (fd.ShowDialog() != DialogResult.OK)
                return;
            byte[] Content = System.IO.File.ReadAllBytes(fd.FileName);
            System.IO.File.WriteAllBytes(fd.FileName, PSBStrMan.ExtractMDF(Content));
            MessageBox.Show("Finished");
        }

        private void b2_Click(object sender, EventArgs e)
        {
            System.Windows.Forms.FolderBrowserDialog dialog = new System.Windows.Forms.FolderBrowserDialog();
            dialog.Description = "Please select the scn2ks folder";
            System.Windows.Forms.FolderBrowserDialog dialog1 = new System.Windows.Forms.FolderBrowserDialog();
            dialog1.Description = "Please select the scn folder";
            if ((dialog1.ShowDialog() == System.Windows.Forms.DialogResult.OK) & (dialog.ShowDialog() == System.Windows.Forms.DialogResult.OK))
            {
                DialogResult dr = MessageBox.Show("Would you like to compress the script? (Recommended)\n\nDoes not work with old games.", "ScnEditorGUI", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                string path = dialog.SelectedPath;
                string path1 = dialog1.SelectedPath;
                string des = path + "(ks2scn)\\";
                if (Directory.Exists(des) == false)
                {
                    Directory.CreateDirectory(des);
                }
                List<string> fl = GetFiles(path, ".scn2ks");
                List<string> fl1 = GetFiles(path1, ".scn");
                foreach (string s in fl)
                {
                    string name = Path.GetFileNameWithoutExtension(s);
                    string ss = "";
                    foreach (string sss in fl1)
                    {
                        if (Path.GetFileNameWithoutExtension(s).Equals(Path.GetFileNameWithoutExtension(sss)))
                        {
                            ss = sss;
                            break;
                        }
                    }
                    SCN = new PSBAnalyzer(File.ReadAllBytes(ss));
                    SCN.Import();
                    SCN.CompressPackget = dr == DialogResult.Yes;
                    string[] Strings = File.ReadAllLines(s);
                    foreach (string o in Strings)
                    {
                        Console.WriteLine(o);
                    }
                    PSBStrMan.CompressionLevel = CompressionLevel.Z_BEST_COMPRESSION; //opitional
                    byte[] outfile = SCN.Export(Strings);
                    System.IO.File.WriteAllBytes(des + name + ".scn", outfile);
                }
                MessageBox.Show("File Saved.");
            }
            
        }

        private void b1_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog dialog = new FolderBrowserDialog();
            dialog.Description = "Please select the scn folder";
            if (dialog.ShowDialog() == DialogResult.OK)
            {
                string path = dialog.SelectedPath;
                string des = path + "(scn2ks)\\";
                if (Directory.Exists(des) == false)
                {
                    Directory.CreateDirectory(des);
                }

                List<string> fl = GetFiles(path, ".scn");
                foreach (string s in fl)
                {
                    OpenFile(s, des);
                }
                MessageBox.Show("done.");
            }
        }
    }
}
