package org.mozilla.intl.chardet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileCharsetDetector {
  private boolean found = false;
  private String encoding = null;
  private String sou = "";
  private int id = 0;
  private String code = "";

  public FileCharsetDetector(String sou, int id, String code) {
    this.sou = sou;
    this.id = id;
    this.code = code;
    try {
      work();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public List<String> getFile(File file) {
    List<String> listLocal = new ArrayList<>();
    if (file != null) {
      File[] f = file.listFiles();
      if (f != null) {
        for (int i = 0; i < f.length; i++) {
          List<String> listson = getFile(f[i]);
          for (String s : listson) {
            listLocal.add(s);
          }
          listLocal.add(f[i].toString());
        }
      } else {
      }
    }
    return listLocal;
  }

  public void work() throws Exception {
    List<String> kzm = new ArrayList<String>();
    kzm.add(".ks");
    kzm.add(".asd");
    kzm.add(".tjs");
    kzm.add(".csv");
    String des = sou + "\\(recoded)";
    List<String> a = getFile(new File(sou));
    Collections.reverse(a);
    List<String> b = getFile(new File(des));
    List<String> c = new ArrayList<String>();
    File file = new File(des);
    file.mkdir();
    for (String s : a) {
      int len = s.length();
      for (String i : kzm) {
        if (s.substring(len - i.length()).equals(i)) {
          Boolean flag = true;
          for (String ss : b) {
            if (new File(s).getName().equals(new File(ss).getName())) {
              flag = false;
              break;
            }
          }
          if (flag == false) {
            break;
          }
          flag = true;
          for (String ss : c) {
            if (new File(s).getName().equals(ss)) {
              flag = false;
              break;
            }
          }
          if (flag == false) {
            break;
          }
          File file1 = new File(s);
          String ssr = "shift-JIS";
          if (id == 2) {
            ssr = guessFileEncoding(file1);
          }
          if (id == 3) {
            ssr = code;
          }
          c.add(file1.getName());
          scanner reader = new scanner(ssr, s, des + "\\" + file1.getName());
          break;
        }
      }
    }

  }

  /**
   * 传入一个文件(File)对象，检查文件编码
   * 
   * @param file File对象实例
   * @return 文件编码，若无，则返回null
   * @throws FileNotFoundException
   * @throws IOException
   */
  public String guessFileEncoding(File file) throws FileNotFoundException, IOException {
    return guessFileEncoding(file, new nsDetector());
  }

  /**
   * <pre>
   * 获取文件的编码
   * &#64;param file
   *            File对象实例
   * &#64;param languageHint
   *            语言提示区域代码 @see #nsPSMDetector ,取值如下：
   *             1 : Japanese
   *             2 : Chinese
   *             3 : Simplified Chinese
   *             4 : Traditional Chinese
   *             5 : Korean
   *             6 : Dont know(default)
   * </pre>
   * 
   * @return 文件编码，eg：UTF-8,GBK,GB2312形式(不确定的时候，返回可能的字符编码序列)；若无，则返回null
   * @throws FileNotFoundException
   * @throws IOException
   */
  public String guessFileEncoding(File file, int languageHint)
      throws FileNotFoundException, IOException {
    return guessFileEncoding(file, new nsDetector(languageHint));
  }

  /**
   * 获取文件的编码
   * 
   * @param file
   * @param det
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private String guessFileEncoding(File file, nsDetector det)
      throws FileNotFoundException, IOException {
    // Set an observer...
    // The Notify() will be called when a matching charset is found.
    det.Init(new nsICharsetDetectionObserver() {
      @Override
      public void Notify(String charset) {
        encoding = charset;
        found = true;
      }
    });

    BufferedInputStream imp = new BufferedInputStream(new FileInputStream(file));
    byte[] buf = new byte[1024];
    int len;
    boolean done = false;
    boolean isAscii = false;

    while ((len = imp.read(buf, 0, buf.length)) != -1) {
      // Check if the stream is only ascii.
      isAscii = det.isAscii(buf, len);
      if (isAscii) {
        break;
      }
      // DoIt if non-ascii and not done yet.
      done = det.DoIt(buf, len, false);
      if (done) {
        break;
      }
    }
    imp.close();
    det.DataEnd();

    if (isAscii) {
      encoding = "ASCII";
      found = true;
    }

    if (!found) {
      String[] prob = det.getProbableCharsets();
      // 这里将可能的字符集组合起来返回
      for (int i = 0; i < prob.length; i++) {
        if (i == 0) {
          encoding = prob[i];
        } else {
          encoding += "," + prob[i];
        }
      }

      if (prob.length > 0) {
        // 在没有发现情况下,也可以只取第一个可能的编码,这里返回的是一个可能的序列
        return encoding;
      } else {
        return null;
      }
    }
    return encoding;
  }
}
