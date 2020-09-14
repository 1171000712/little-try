package org.mozilla.intl.chardet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileView;

public class gui {
  static int id = 0;

  public static void main(String[] args) {
    JFrame frame = new JFrame("文本转码娘");
    frame.setResizable(false);
    frame.setBounds(300, 300, 200, 300);
    frame.setIconImage(new ImageIcon("./jre/bin/plugin2/1.png").getImage());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    panel.setLayout(null);
    JComboBox<String> c1 = new JComboBox<String>();
    c1.setBounds(20, 30, 140, 20);
    c1.addItem("-请选择默认编码-");
    c1.addItem("Shift-JIS");
    c1.addItem("自动识别（不推荐）");
    c1.addItem("输入编码");
    JTextField jt = new JTextField();
    jt.setBounds(20, 120, 140, 20);
    jt.setVisible(false);
    JButton jb = new JButton("打开文件夹");
    jb.setBounds(30, 200, 120, 20);
    jb.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (id == 0) {
          JOptionPane.showMessageDialog(null, "请选择默认编码!", "", JOptionPane.PLAIN_MESSAGE);
          return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileView(new FileView() {
          @Override
          public Icon getIcon(File f) {
            return fileChooser.getFileSystemView().getSystemIcon(f);
          }
        });
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          String filePath = fileChooser.getSelectedFile().getAbsolutePath();
          FileCharsetDetector f = new FileCharsetDetector(filePath, id, jt.getText());
          JOptionPane.showMessageDialog(null, "done.", "", JOptionPane.PLAIN_MESSAGE);
        }
      }
    });
    c1.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        id = Integer.valueOf(c1.getSelectedIndex());
        if (id == 3) {
          jt.setVisible(true);
        } else {
          jt.setVisible(false);
        }
      }
    });
    panel.add(jt);
    panel.add(c1);
    panel.add(jb);
    frame.add(panel);
    frame.setVisible(true);
  }
}
