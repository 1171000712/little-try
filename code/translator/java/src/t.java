import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class t {
  private static String APP_ID;
  private static String SECURITY_KEY;
  public static int cnt = 0;
  public static int max = 3;
  public state state;
  public String sou;
  public GUI gui;
  public static int sum = 0;
  public static int success = 0;
  public static int fail = 0;

  public t(GUI g, state state, String APP_ID, String SECURITY_KEY, String sou) {
    this.state = state;
    this.APP_ID = APP_ID;
    this.SECURITY_KEY = SECURITY_KEY;
    this.sou = sou;
    this.gui = gui;
    if (state.tid == 2) {
      max = 6;
    }
    if (state.p[5] == 0) {
      max = 1;
    }
    work();
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

  public void work() {
    List<String> kzm = new ArrayList<String>();
    kzm.add(".ks");
    kzm.add(".scn2ks");
    String des = sou + "(translated)";
    List<String> a = getFile(new File(sou));
    List<String> b = getFile(new File(des));
    Collections.reverse(a);
    for (String s : a) {
      Boolean flag = true;
      for (String ss : b) {
        if (new File(s).getName().equals(new File(ss).getName())) {
          flag = false;
          break;
        }
      }
      if (flag == false) {
        continue;
      }
      int len = s.length();
      int u = -1;
      for (String i : kzm) {
        u = u + 1;
        if (s.substring(len - i.length()).equals(i)) {
          sum = sum + 1;
        }
      }
    }
    for (String s : a) {
      Boolean flag = true;
      for (String ss : b) {
        if (new File(s).getName().equals(new File(ss).getName())) {
          flag = false;
          break;
        }
      }
      if (flag == false) {
        continue;
      }
      int len = s.length();
      int u = -1;
      for (String i : kzm) {
        u = u + 1;
        if (s.substring(len - i.length()).equals(i)) {
          File file = new File(s.replace(sou, des));
          File fileParent = file.getParentFile();
          if (!fileParent.exists()) {
            fileParent.mkdirs();
          }
          while (cnt >= max) {
            try {
              Thread.sleep(500);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          scanner scanner = new scanner(gui, state, APP_ID, SECURITY_KEY, s, file, 0);
        }

      }
    }
  }
}
