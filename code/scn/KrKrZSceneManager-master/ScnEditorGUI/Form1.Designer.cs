namespace ScnEditorGUI
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.listBox1 = new System.Windows.Forms.ListBox();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.openFileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveFileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.huffmanToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.decompressImageToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.compressImageToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.decompressScriptToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.tryRecoveryToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.tJS2ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ClipboardSeekSample = new System.Windows.Forms.ToolStripMenuItem();
            this.SeekUpdate = new System.Windows.Forms.Timer(this.components);
            this.b1 = new System.Windows.Forms.Button();
            this.b2 = new System.Windows.Forms.Button();
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // listBox1
            // 
            this.listBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.listBox1.FormattingEnabled = true;
            this.listBox1.Location = new System.Drawing.Point(12, 12);
            this.listBox1.Name = "listBox1";
            this.listBox1.Size = new System.Drawing.Size(435, 277);
            this.listBox1.TabIndex = 0;
            this.listBox1.SelectedIndexChanged += new System.EventHandler(this.listBox1_SelectedIndexChanged);
            // 
            // textBox1
            // 
            this.textBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.textBox1.Location = new System.Drawing.Point(12, 290);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(435, 21);
            this.textBox1.TabIndex = 1;
            this.textBox1.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textBox1_KeyPress);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.openFileToolStripMenuItem,
            this.saveFileToolStripMenuItem,
            this.huffmanToolStripMenuItem,
            this.tryRecoveryToolStripMenuItem,
            this.tJS2ToolStripMenuItem,
            this.ClipboardSeekSample});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(167, 136);
            // 
            // openFileToolStripMenuItem
            // 
            this.openFileToolStripMenuItem.Name = "openFileToolStripMenuItem";
            this.openFileToolStripMenuItem.Size = new System.Drawing.Size(166, 22);
            this.openFileToolStripMenuItem.Text = "Open File";
            this.openFileToolStripMenuItem.Click += new System.EventHandler(this.openFileToolStripMenuItem_Click);
            // 
            // saveFileToolStripMenuItem
            // 
            this.saveFileToolStripMenuItem.Name = "saveFileToolStripMenuItem";
            this.saveFileToolStripMenuItem.Size = new System.Drawing.Size(166, 22);
            this.saveFileToolStripMenuItem.Text = "Save File";
            this.saveFileToolStripMenuItem.Click += new System.EventHandler(this.saveFileToolStripMenuItem_Click);
            // 
            // huffmanToolStripMenuItem
            // 
            this.huffmanToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.decompressImageToolStripMenuItem,
            this.compressImageToolStripMenuItem,
            this.decompressScriptToolStripMenuItem});
            this.huffmanToolStripMenuItem.Name = "huffmanToolStripMenuItem";
            this.huffmanToolStripMenuItem.Size = new System.Drawing.Size(166, 22);
            this.huffmanToolStripMenuItem.Text = "Compress";
            this.huffmanToolStripMenuItem.Visible = false;
            // 
            // decompressImageToolStripMenuItem
            // 
            this.decompressImageToolStripMenuItem.Name = "decompressImageToolStripMenuItem";
            this.decompressImageToolStripMenuItem.Size = new System.Drawing.Size(190, 22);
            this.decompressImageToolStripMenuItem.Text = "Decompress Image";
            this.decompressImageToolStripMenuItem.Visible = false;
            this.decompressImageToolStripMenuItem.Click += new System.EventHandler(this.decompressImageToolStripMenuItem_Click);
            // 
            // compressImageToolStripMenuItem
            // 
            this.compressImageToolStripMenuItem.Name = "compressImageToolStripMenuItem";
            this.compressImageToolStripMenuItem.Size = new System.Drawing.Size(190, 22);
            this.compressImageToolStripMenuItem.Text = "Compress Image";
            this.compressImageToolStripMenuItem.Visible = false;
            this.compressImageToolStripMenuItem.Click += new System.EventHandler(this.compressImageToolStripMenuItem_Click);
            // 
            // decompressScriptToolStripMenuItem
            // 
            this.decompressScriptToolStripMenuItem.Name = "decompressScriptToolStripMenuItem";
            this.decompressScriptToolStripMenuItem.Size = new System.Drawing.Size(190, 22);
            this.decompressScriptToolStripMenuItem.Text = "Decompress Script";
            this.decompressScriptToolStripMenuItem.Click += new System.EventHandler(this.decompressScriptToolStripMenuItem_Click);
            // 
            // tryRecoveryToolStripMenuItem
            // 
            this.tryRecoveryToolStripMenuItem.Name = "tryRecoveryToolStripMenuItem";
            this.tryRecoveryToolStripMenuItem.Size = new System.Drawing.Size(166, 22);
            this.tryRecoveryToolStripMenuItem.Text = "Try Recovery";
            this.tryRecoveryToolStripMenuItem.Visible = false;
            this.tryRecoveryToolStripMenuItem.Click += new System.EventHandler(this.tryRecoveryToolStripMenuItem_Click);
            // 
            // tJS2ToolStripMenuItem
            // 
            this.tJS2ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.openToolStripMenuItem,
            this.saveToolStripMenuItem});
            this.tJS2ToolStripMenuItem.Name = "tJS2ToolStripMenuItem";
            this.tJS2ToolStripMenuItem.Size = new System.Drawing.Size(166, 22);
            this.tJS2ToolStripMenuItem.Text = "TJS2";
            this.tJS2ToolStripMenuItem.Visible = false;
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.Size = new System.Drawing.Size(108, 22);
            this.openToolStripMenuItem.Text = "Open";
            this.openToolStripMenuItem.Click += new System.EventHandler(this.openToolStripMenuItem_Click);
            // 
            // saveToolStripMenuItem
            // 
            this.saveToolStripMenuItem.Name = "saveToolStripMenuItem";
            this.saveToolStripMenuItem.Size = new System.Drawing.Size(108, 22);
            this.saveToolStripMenuItem.Text = "Save";
            this.saveToolStripMenuItem.Click += new System.EventHandler(this.saveToolStripMenuItem_Click);
            // 
            // ClipboardSeekSample
            // 
            this.ClipboardSeekSample.Checked = true;
            this.ClipboardSeekSample.CheckOnClick = true;
            this.ClipboardSeekSample.CheckState = System.Windows.Forms.CheckState.Checked;
            this.ClipboardSeekSample.Name = "ClipboardSeekSample";
            this.ClipboardSeekSample.Size = new System.Drawing.Size(166, 22);
            this.ClipboardSeekSample.Text = "Seek Clipboard";
            this.ClipboardSeekSample.Visible = false;
            this.ClipboardSeekSample.Click += new System.EventHandler(this.ClipboardSeekSample_Click);
            // 
            // SeekUpdate
            // 
            this.SeekUpdate.Enabled = true;
            this.SeekUpdate.Interval = 500;
            this.SeekUpdate.Tick += new System.EventHandler(this.SeekUpdate_Tick);
            // 
            // b1
            // 
            this.b1.Location = new System.Drawing.Point(24, 28);
            this.b1.Name = "b1";
            this.b1.Size = new System.Drawing.Size(75, 21);
            this.b1.TabIndex = 1;
            this.b1.Text = "Open files";
            this.b1.Click += new System.EventHandler(this.b1_Click);
            // 
            // b2
            // 
            this.b2.Location = new System.Drawing.Point(24, 106);
            this.b2.Name = "b2";
            this.b2.Size = new System.Drawing.Size(75, 21);
            this.b2.TabIndex = 2;
            this.b2.Text = "Save files";
            this.b2.Click += new System.EventHandler(this.b2_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.ClientSize = new System.Drawing.Size(128, 154);
            this.ContextMenuStrip = this.contextMenuStrip1;
            this.Controls.Add(this.b1);
            this.Controls.Add(this.b2);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form1";
            this.Text = "scn解码娘";
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListBox listBox1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem openFileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveFileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem huffmanToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem decompressImageToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem compressImageToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem tryRecoveryToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem tJS2ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem ClipboardSeekSample;
        private System.Windows.Forms.Timer SeekUpdate;
        private System.Windows.Forms.ToolStripMenuItem decompressScriptToolStripMenuItem;
        private System.Windows.Forms.Button b1;
        private System.Windows.Forms.Button b2;
    }
}

