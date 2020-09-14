package org.mozilla.intl.chardet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class scanner extends Thread {
  private String type;
  private String str;
  private String des;

  public scanner(String type, String filename, String des) {
    this.type = type;
    this.str = filename;
    this.des = des;
    start();
  }


  @Override
  public void run() {
    String message = "";
    File file = new File(str);
    if (file.isFile() && file.exists()) {
      BufferedReader br = null;
      try {
        UnicodeReader r = new UnicodeReader(new FileInputStream(file), type);
        br = new BufferedReader(r);
      } catch (FileNotFoundException e2) {
        // TODO Auto-generated catch block
        e2.printStackTrace();
      }
      String buff;
      try {
        while ((buff = br.readLine()) != null) {
          message = message + buff + "\n";
          System.out.println(buff);
        }
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      try {
        br.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    try {
      write(message);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void write(String message) throws UnsupportedEncodingException, FileNotFoundException {
    File file = new File(des);
    FileOutputStream fos = new FileOutputStream(file);
    try {
      fos.write(new byte[] {(byte) 0xFF, (byte) 0xFE});
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "utf-16LE")));
    out.write(message);
    out.flush();
    out.close();
  }


}

