import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;
import com.baidu.translate.demo.TransApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class scanner extends Thread {
  private String APP_ID;
  private String SECURITY_KEY;
  private String str;
  private File file;
  private int id;
  private state state;
  private GUI gui;
  private boolean fff = false;

  public static String k(String s) {
    String[] c = {"*", "+", "[", "]", "{", "}", "(", ")", "."};
    String ans = s;
    for (String cc : c) {
      ans = ans.replaceAll("\\" + cc, "\\\\" + cc);
    }
    return ans;
  }

  public scanner(GUI gui, state state, String APP_ID, String SECURITY_KEY, String filename,
      File file, int id) {
    this.APP_ID = APP_ID;
    this.SECURITY_KEY = SECURITY_KEY;
    this.str = filename;
    this.file = file;
    this.id = id;
    t.cnt = t.cnt + 1;
    this.state = state;
    this.gui = gui;
    start();
  }

  public void failed() {
    if (fff) {
      return;
    }
    t.fail = t.fail + 1;
    t.cnt = t.cnt - 1;
    fff = true;
    gui.add(file.getName() + "...failed");
  }

  @SuppressWarnings("deprecation")
  public List<String> trans3(String s) {
    List<String> list = new ArrayList<String>();
    try {


      URL url = new URL("http://fanyi.youdao.com/openapi.do");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.addRequestProperty("encoding", "UTF-8");
      connection.setDoInput(true);
      connection.setDoOutput(true);

      connection.setRequestMethod("POST");

      OutputStream os = connection.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os);
      BufferedWriter bw = new BufferedWriter(osw);

      String ss = "keyfrom=fadabvaa&key=522071532&type=data&doctype=json&version=1.1&q=" + s;
      bw.write(ss);
      bw.flush();

      InputStream is = connection.getInputStream();
      InputStreamReader isr = new InputStreamReader(is, "UTF-8");
      BufferedReader br = new BufferedReader(isr);

      String line;
      StringBuilder builder = new StringBuilder();
      while ((line = br.readLine()) != null) {
        builder.append(line);
      }

      bw.close();
      osw.close();
      os.close();

      br.close();
      isr.close();
      is.close();

      String a = (builder.toString());
      Pattern p = Pattern.compile("translation\":\\[(.+)\\]");
      Matcher m = p.matcher(a);
      while (m.find()) {
        String l = m.group(1);
        while (l.indexOf('\"') != -1) {
          l = l.substring(1);
          int t = l.indexOf('\"');
          if (t == -1) {
            break;
          }
          if (!l.substring(0, t).equals(",")) {
            list.add(StringEscapeUtils.unescapeJava(l.substring(0, t)));
          }
          l = l.substring(t);
        }
      }
    } catch (MalformedURLException e) {
      failed();
      e.printStackTrace();
    } catch (IOException e) {
      failed();
      e.printStackTrace();
    }
    if (list.size() == 0) {
      failed();
    }
    return list;
  }

  @SuppressWarnings("deprecation")
  public List<String> trans(String s) throws UnsupportedEncodingException {
    TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    String query = s;
    String str = null;
    try {
      str = api.getTransResult(query, "auto", "zh");
    } catch (Exception e) {
      // TODO: handle exception
      failed();
      return null;
    }
    System.out.println(str);
    JsonObject jsonObj = (JsonObject) new JsonParser().parse(str);
    if (jsonObj.get("trans_result") == null) {
      failed();
      return null;
    }
    String res = jsonObj.get("trans_result").toString();
    JsonArray js = new JsonParser().parse(res).getAsJsonArray();
    List<String> anString = new ArrayList<String>();
    for (int i = 0; i < js.size(); i++) {
      anString
          .add(StringEscapeUtils.unescapeJava(((JsonObject) js.get(i)).get("dst").getAsString()));
    }
    return (anString);
  }

  @SuppressWarnings("deprecation")
  public List<String> trans2(String s) {
    List<String> ans = new ArrayList<String>();
    String[] ss = s.split("\n");
    String reg = "[\u4e00-\u9fa5||、||·||.||,||!||…||—||:||-|| ]+";
    List<FanyiV3Demo> f = new ArrayList<FanyiV3Demo>();
    for (String ssr : ss) {
      ssr = ssr.replaceAll(" ", "");
      if (ssr.matches(reg)) {
        ans.add(ssr);
      } else {
        try {
          ans.add("");
          f.add(new FanyiV3Demo(APP_ID, SECURITY_KEY, ssr));
        } catch (IOException e) {
          failed();
          // e.printStackTrace();
        }
      }
    }
    Boolean flag = true;
    while (flag) {
      flag = false;
      for (FanyiV3Demo ff : f) {
        if (ff.getans().equals("")) {
          flag = true;
          break;
        }
        if (ff.getans().equals("Lyx727656822!@#")) {
          failed();
          return null;
        }
      }
      // System.out.println("..");
      // System.out.println("...");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        failed();
        e.printStackTrace();
      }
    }
    int x = 0;
    for (FanyiV3Demo ff : f) {
      while (!ans.get(x).equals("")) {
        x = x + 1;
      }
      ans.set(x, ff.getans());
    }
    return ans;
  }

  @Override
  public void run() {
    List<String> list = new ArrayList<String>();
    File file = new File(str);
    if (file.isFile() && file.exists()) {
      if (id == 0) {
        BufferedReader br = null;
        try {
          UnicodeReader r = new UnicodeReader(new FileInputStream(file), "shift-JIS");
          br = new BufferedReader(r);
        } catch (FileNotFoundException e2) {
          failed();
          // t.cnt = t.cnt - 1;
          return;
          // e2.printStackTrace();
        }
        String buff;
        try {
          while ((buff = br.readLine()) != null) {
            list.add(buff);
          }
        } catch (IOException e1) {
          failed();
          // t.cnt = t.cnt - 1;
          return;
          // e1.printStackTrace();
        }
        try {
          br.close();
        } catch (IOException e) {
          failed();
          // t.cnt = t.cnt - 1;
          return;
          // e.printStackTrace();
        }
      } else {
        try (FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader)) {
          String line;
          String m = "";
          while ((line = br.readLine()) != null) {
            list.add(line);
          }
          br.close();
        } catch (FileNotFoundException e) {
          failed();
          // t.cnt = t.cnt - 1;
          return;
          // e.printStackTrace();
        } catch (IOException e) {
          failed();
          // t.cnt = t.cnt - 1;
          return;
          // e.printStackTrace();
        }

      }
      try {
        work(list);
      } catch (UnsupportedEncodingException e) {
        failed();
        // t.cnt = t.cnt - 1;
        return;
        // e.printStackTrace();
      }
    }
    // t.cnt = t.cnt - 1;
  }

  public void work(List<String> list) throws UnsupportedEncodingException {
    String meassage = "";
    int x = -1;
    List<Integer> num = new ArrayList<Integer>();
    List<String> now = new ArrayList<String>();
    Boolean open = false;
    if (state.signalstring.equals("")) {
      open = true;
    }
    char[] fc = {'(', '<', '[', '{'};
    char[] bc = {')', '>', ']', '}'};
    for (String s : list) {
      x = x + 1;
      // System.out.println(str + " " + String.valueOf(x) + ":" + s);
      if (!open) {
        Pattern patternp = Pattern.compile(state.signalstring);
        Matcher mmmm = patternp.matcher(s);
        if (mmmm.find()) {
          open = true;
          continue;
        }
        continue;
      }
      if (!state.deletestring.equals("[]")) {
        Pattern p = Pattern.compile(state.deletestring);
        Matcher m = p.matcher(s);
        if ((m.find())) {
          continue;
        }
      }
      if (!state.containstring.equals("")) {
        Pattern pp = Pattern.compile(state.containstring);
        Matcher mm = pp.matcher(s);
        if ((!mm.find())) {
          continue;
        }
      }
      int n = 0;
      int[] cnt = {0, 0, 0, 0};
      String ll = "";
      for (int i = 1; i <= s.length(); i++) {
        boolean flag = true;
        for (int j = 0; j < 4; j++) {
          if (state.p[j] == 0) {
            continue;
          }
          if ((cnt[j] == 1) && (s.substring(i - 1, i).contains(String.valueOf(bc[j])))) {
            continue;
          }
          if (cnt[j] > 0) {
            flag = false;
          }
        }
        if (!flag) {
          continue;
        }
        flag = true;
        String sss = s.substring(i - 1, i);
        for (int j = 0; j < 4; j++) {
          if (state.p[j] == 0) {
            continue;
          }
          if (sss.contains(String.valueOf(fc[j]))) {
            cnt[j] = cnt[j] + 1;
            flag = false;
          }
          if (sss.contains(String.valueOf(bc[j]))) {
            cnt[j] = cnt[j] - 1;
            n = i;
            flag = false;
          }
        }
        Pattern p1 = Pattern.compile("[\u3040-\u309F\u30A0-\u30FF\u4E00-\u9FA5\uF900-\uFA2D]");
        Matcher m1 = p1.matcher(sss);
        if (((!m1.find()) && (state.p[4] == 1) && (!(sss.equals(":") && (n < i - 2)))
            && (!(sss.equals("—") && (n < i - 2))) && (!(sss.equals("-") && (n < i - 2)))
            && (!(sss.equals("·") && (n < i - 2))) && (!(sss.equals(".") && (n < i - 2)))
            && (!(sss.equals(",") && (n < i - 2))) && (!(sss.equals("!") && (n < i - 2)))
            && (!(sss.equals("…") && (n < i - 2))) && (!(sss.equals("、") && (n < i - 2)))
            && (!(sss.equals(" ") && (n < i - 2)))) || (!flag)) {
          if (n < i - 1) {
            String reg = "[\u4e00-\u9fa5||、||·||.||,||!||…||—||:||-|| ]+";
            if (!s.substring(n, i - 1).matches(reg)) {
              ll = ll + (s.substring(n, i - 1) + "\n");
              System.out.println(s.substring(n, i - 1));
            }
          }
          n = i;
        }
      }
      if (n < s.length()) {
        String reg = "[\u4e00-\u9fa5||、||·||.||,||!||…||—||:||-|| ]+";
        if (!s.substring(n, s.length()).matches(reg)) {
          ll = ll + (s.substring(n, s.length()) + "\n");
        }
      }
      if (ll.length() > 0) {
        now.add(ll.substring(0, ll.length() - 1));
        num.add(x);
        if (now.toString().length() >= 600) {
          String l = "";
          for (String ss : now) {
            l = l + ss + "\n";
          }
          List<String> anStrings = null;
          if (state.tid == 1) {
            anStrings = trans(l);
          }
          if (state.tid == 2) {
            anStrings = trans2(l);
          }
          if (state.tid == 3) {
            anStrings = trans3(l);
          }
          if (state.p[5] == 0) {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              // e.printStackTrace();
              failed();
              return;
            }
          }
          int t = -1;
          for (int i = 0; i < num.size(); i++) {
            int nu = num.get(i);
            String[] de = now.get(i).split("\n");
            String ans = list.get(nu);
            for (String ssr : de) {
              t = t + 1;
              try {
                ans = ans.replaceFirst(k(ssr), anStrings.get(t));
              } catch (Exception e) {
                // TODO: handle exception
                // e.printStackTrace();
                failed();
                return;
              }
            }
            list.set(nu, ans);
          }
          num = new ArrayList<Integer>();
          now = new ArrayList<String>();
        }
      }
    }

    if (now.size() > 0) {
      String l = "";
      for (String ss : now) {
        l = l + ss + "\n";
      }
      l = l.substring(0, l.length() - 1);
      List<String> anStrings = null;
      if (state.tid == 1) {
        anStrings = trans(l);
      }
      if (state.tid == 2) {
        anStrings = trans2(l);
      }
      if (state.tid == 3) {
        anStrings = trans3(l);
      }
      if (state.p[5] == 0) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          // e.printStackTrace();
          failed();
          return;
        }
      }
      int t = -1;
      for (int i = 0; i < num.size(); i++) {
        int nu = num.get(i);
        String[] de = now.get(i).split("\n");
        String ans = list.get(nu);
        for (String ssr : de) {
          t = t + 1;
          try {
            ans = ans.replaceFirst(k(ssr), anStrings.get(t));
          } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
            failed();
            return;
          }
        }
        list.set(nu, ans);
      }
    }

    for (

    String s : list) {
      if (id == 0) {
        meassage = meassage + s + "\n";
      } else {
        meassage = meassage + s + "\r\n";
      }
    }
    try {
      write(meassage);
      // System.out.println(str);
    } catch (FileNotFoundException e) {
      failed();
      return;
      // TODO Auto-generated catch block e.printStackTrace(); }
    }
  }

  public void write(String message) throws UnsupportedEncodingException, FileNotFoundException {
    if (!fff) {

      if (id == 0) {
        FileOutputStream fos = new FileOutputStream(file);
        try {
          fos.write(new byte[] {(byte) 0xFF, (byte) 0xFE});
        } catch (IOException e) {
          failed();
          return;
          // TODO Auto-generated catch block
        }
        PrintWriter out =
            new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "utf-16LE")));
        out.write(message);
        // out.flush();
        out.close();
        gui.add(file.getName() + "....OK");
        t.success = t.success + 1;
        t.cnt = t.cnt - 1;
      } else {

        try {
          file.createNewFile();
        } catch (IOException e2) {
          failed();
          return;
          // e2.printStackTrace();
        }
        FileWriter writer = null;
        try {
          writer = new FileWriter(file);
        } catch (IOException e1) {
          failed();
          return;
          // e1.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(writer);
        try {
          bw.write(message);
        } catch (IOException e) {
          failed();
          return;
          // TODO Auto-generated catch block
          // e.printStackTrace();
        }
        try {
          // bw.flush();
          bw.close();
          gui.add(file.getName() + "....OK");
          t.success = t.success + 1;
          t.cnt = t.cnt - 1;
        } catch (IOException e) {
          failed();
          return;
          // TODO Auto-generated catch block
          // e.printStackTrace();
        }
      }
    }
  }


}
