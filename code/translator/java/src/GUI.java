import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileView;

public class GUI {
  public static String APP_ID = "";
  public static String SECURITY_KEY = "";
  public static int type = 0;
  public static JTextArea jTextArea = new JTextArea();

  public static void readini() throws IOException {
    try (FileReader reader = new FileReader(new File("save.ini"));
        BufferedReader br = new BufferedReader(reader)) {
      type = Integer.valueOf(br.readLine().trim());
      APP_ID = br.readLine();
      SECURITY_KEY = br.readLine();
      br.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    }
  }

  public static void add(String s) {
    jTextArea.append(s + "\r\n");
    jTextArea.setCaretPosition(jTextArea.getText().length());
  }


  public static void main(String[] args) throws IOException {
    final GUI gui = new GUI();
    readini();
    final JFrame frame = new JFrame("文本翻译姬");
    frame.setResizable(false);
    frame.setBounds(500, 300, 350, 200);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setIconImage(new ImageIcon("./jre/bin/plugin2/11.png").getImage());
    final JPanel panel = new JPanel();
    final JPanel panel2 = new JPanel();
    final JPanel panel3 = new JPanel();
    panel.setLayout(null);
    panel2.setLayout(null);
    panel3.setLayout(null);
    jTextArea.setBounds(0, 0, 300, 190);
    jTextArea.setEditable(false);
    JScrollPane jsp = new JScrollPane(jTextArea);
    jsp.setBounds(0, 0, 350, 190);
    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    panel3.add(jsp);
    JLabel jll1 = new JLabel("过滤字符：");
    final JTextField jj1 = new JTextField("{||}||;");
    jll1.setBounds(10, 5, 80, 20);
    jj1.setBounds(100, 5, 200, 20);
    panel2.add(jll1);
    panel2.add(jj1);
    JLabel jll2 = new JLabel("标志句子：");
    final JTextField jj2 = new JTextField("");
    jll2.setBounds(10, 30, 80, 20);
    jj2.setBounds(100, 30, 200, 20);
    panel2.add(jll2);
    panel2.add(jj2);
    JLabel jll3 = new JLabel("包含字符串：");
    final JTextField jj3 = new JTextField("");
    jll3.setBounds(10, 55, 80, 20);
    jj3.setBounds(100, 55, 200, 20);
    panel2.add(jll3);
    panel2.add(jj3);
    final JCheckBox jcb1 = new JCheckBox("忽略()中内容");
    jcb1.setBounds(10, 80, 150, 20);
    panel2.add(jcb1);
    final JCheckBox jcb2 = new JCheckBox("忽略<>中内容");
    jcb2.setBounds(180, 80, 150, 20);
    panel2.add(jcb2);
    final JCheckBox jcb3 = new JCheckBox("忽略[]中内容");
    jcb3.setBounds(10, 105, 150, 20);
    panel2.add(jcb3);
    final JCheckBox jcb4 = new JCheckBox("忽略{}中内容");
    jcb4.setBounds(180, 105, 150, 20);
    panel2.add(jcb4);
    final JCheckBox jcb5 = new JCheckBox("忽略非日文内容");
    jcb5.setBounds(10, 130, 150, 20);
    jcb5.setSelected(true);
    panel2.add(jcb5);
    final JCheckBox jcb6 = new JCheckBox("开启多线程");
    jcb6.setBounds(10, 155, 150, 20);
    jcb6.setSelected(true);
    panel2.add(jcb6);
    JButton jbb = new JButton("选择文件夹");
    jbb.setBounds(180, 140, 150, 20);
    jbb.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileView(new FileView() {
          @Override
          public Icon getIcon(File f) {
            return fileChooser.getFileSystemView().getSystemIcon(f);
          }
        });
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          panel2.setVisible(false);
          frame.add(panel3);
          frame.setVisible(true);
          final String filePath = fileChooser.getSelectedFile().getAbsolutePath();
          int[] p = {0, 0, 0, 0, 0, 0};
          if (jcb1.isSelected()) {
            p[0] = 1;
          }
          if (jcb2.isSelected()) {
            p[1] = 1;
          }
          if (jcb3.isSelected()) {
            p[2] = 1;
          }
          if (jcb4.isSelected()) {
            p[3] = 1;
          }
          if (jcb5.isSelected()) {
            p[4] = 1;
          }
          if (jcb6.isSelected()) {
            p[5] = 1;
          }
          final state state = new state(type, jj2.getText(), jj1.getText(), jj3.getText(), p);

          final Timer ti = new Timer(true);
          TimerTask tk = new TimerTask() {
            Boolean p = true;

            @Override
            public void run() {
              if (p) {
                p = false;
                t t = new t(gui, state, APP_ID, SECURITY_KEY, filePath);
              } else {
                // TODO Auto-generated method stub
                if (t.success + t.fail >= t.sum) {
                  JOptionPane.showMessageDialog(null, "翻译完成，成功：" + String.valueOf(t.success)
                      + "个，失败：" + String.valueOf(t.fail) + "个。", "", JOptionPane.PLAIN_MESSAGE);
                  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  System.exit(0);
                }
              }
            }
          };
          ti.schedule(tk, 1000, 100);
        }

      }
    });
    panel2.add(jbb);
    JLabel jl = new JLabel("请验证API的ID与密钥");
    JLabel jl1 = new JLabel("ID:");
    JLabel jl2 = new JLabel("密钥:");
    final JCheckBox jcb = new JCheckBox("保存用户信息");
    JButton jb = new JButton("点击验证");
    final JTextField j1 = new JTextField();
    final JPasswordField j2 = new JPasswordField();
    panel.add(jl);
    jl.setBounds(90, 5, 200, 20);
    panel.add(j1);
    j1.setBounds(90, 50, 220, 20);
    jl1.setBounds(40, 50, 80, 20);
    panel.add(j2);
    j2.setBounds(90, 80, 220, 20);
    jl2.setBounds(40, 80, 80, 20);
    panel.add(jcb);
    jcb.setBounds(60, 110, 150, 20);
    panel.add(jb);
    jb.setBounds(210, 110, 100, 20);
    final JRadioButton jrb1 = new JRadioButton("百度");
    final JRadioButton jrb2 = new JRadioButton("有道");
    ButtonGroup bg = new ButtonGroup();
    bg.add(jrb1);
    jrb1.setBounds(80, 25, 80, 25);
    jrb2.setBounds(160, 25, 80, 25);
    bg.add(jrb2);
    jb.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        int tid = 0;
        if (jrb1.isSelected()) {
          tid = 1;
        }
        if (jrb2.isSelected()) {
          tid = 2;
        }
        if (tid == 0) {
          JOptionPane.showMessageDialog(null, "请选择API!", "", JOptionPane.PLAIN_MESSAGE);
          return;
        }
        type = tid;
        APP_ID = j1.getText();
        SECURITY_KEY = j2.getText();
        if (tid == 1) {
          test t = new test(APP_ID, SECURITY_KEY, tid);
          if (t.getans().equals("")) {
            JOptionPane.showMessageDialog(null, "验证失败!", "", JOptionPane.PLAIN_MESSAGE);
            return;
          }
        }
        if (tid == 2) {
          FanyiV3Demo f = null;
          try {
            f = new FanyiV3Demo(APP_ID, SECURITY_KEY, "I am a pig.");
          } catch (IOException e1) {
            // TODO Auto-generated catch block
            // e1.printStackTrace();
          }
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            // e1.printStackTrace();
          }
          if (f.getans().equals("")) {
            JOptionPane.showMessageDialog(null, "验证失败!", "", JOptionPane.PLAIN_MESSAGE);
            return;
          }
        }
        JOptionPane.showMessageDialog(null, "验证成功!", "", JOptionPane.PLAIN_MESSAGE);
        if (jcb.isSelected()) {
          String string = String.valueOf(tid) + "\r\n" + j1.getText() + "\r\n" + j2.getText();
          File file = null;
          try {
            file = new File("save.ini");
            file.createNewFile();
          } catch (IOException e2) {
            e2.printStackTrace();
          }
          FileWriter writer = null;
          try {
            writer = new FileWriter(file);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
          BufferedWriter bw = new BufferedWriter(writer);
          try {
            bw.write(string);
          } catch (IOException e3) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
          }
          try {
            // bw.flush();
            bw.close();
          } catch (IOException e4) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
          }
        }
        panel.setVisible(false);
        frame.add(panel2);
        frame.setVisible(true);
      }
    });
    if (type != 0) {
      if (type == 1) {
        jrb1.setSelected(true);
      }
      if (type == 2) {
        jrb2.setSelected(true);
      }
      j1.setText(APP_ID);
      j2.setText(SECURITY_KEY);
    }
    panel.add(jrb1);
    panel.add(jrb2);
    panel.add(jl1);
    panel.add(jl2);
    frame.add(panel);
    frame.setVisible(true);
  }
}
