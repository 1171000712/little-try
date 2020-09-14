import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class FanyiV3Demo extends Thread {

  private Logger logger = LoggerFactory.getLogger(FanyiV3Demo.class);
  private final String YOUDAO_URL = "https://openapi.youdao.com/api";

  private final String APP_KEY;

  private final String APP_SECRET;

  public String ans = "";
  public String string = "";

  public FanyiV3Demo(String app_key, String APP_SERCET, String string) throws IOException {
    this.string = string;
    this.APP_KEY = app_key;
    this.APP_SECRET = APP_SERCET;
    start();
  }

  public void failed() {
    ans = "Lyx727656822!@#";
  }

  public String getans() {
    return ans;
  }

  @Override
  public void run() {

    Map<String, String> params = new HashMap<String, String>();
    String q = string;
    String salt = String.valueOf(Thread.currentThread().getId());
    params.put("from", "auto");
    params.put("to", "zh-CHS");
    params.put("signType", "v3");
    String curtime = String.valueOf(System.currentTimeMillis() / 1000);
    params.put("curtime", curtime);
    String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
    String sign = getDigest(signStr);
    params.put("appKey", APP_KEY);
    params.put("q", q);
    params.put("salt", salt);
    params.put("sign", sign);
    /** 处理结果 */
    try {
      requestForHttp(YOUDAO_URL, params);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      failed();
      e.printStackTrace();
    }
  }

  public void requestForHttp(String url, Map<String, String> params) throws IOException {

    /** 创建HttpClient */
    CloseableHttpClient httpClient = HttpClients.createDefault();

    /** httpPost */
    HttpPost httpPost = new HttpPost(url);
    List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
    Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> en = it.next();
      String key = en.getKey();
      String value = en.getValue();
      paramsList.add(new BasicNameValuePair(key, value));
    }
    httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
    CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
    try {
      Header[] contentType = httpResponse.getHeaders("Content-Type");
      // logger.info("Content-Type:" + contentType[0].getValue());
      if ("audio/mp3".equals(contentType[0].getValue())) {
        // 如果响应是wav
        HttpEntity httpEntity = httpResponse.getEntity();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        httpResponse.getEntity().writeTo(baos);
        byte[] result = baos.toByteArray();
        EntityUtils.consume(httpEntity);
        if (result != null) {// 合成成功
          String file = "合成的音频存储路径" + System.currentTimeMillis() + ".mp3";
          byte2File(result, file);
        }
      } else {
        /** 响应不是音频流，直接显示结果 */
        HttpEntity httpEntity = httpResponse.getEntity();
        String json = EntityUtils.toString(httpEntity, "UTF-8");
        EntityUtils.consume(httpEntity);
        // logger.info(json);
        // System.out.println(string + "----" + json);
        JsonObject jsonObj = (JsonObject) new JsonParser().parse(json);
        String res = jsonObj.get("translation").toString();
        ans = res.substring(2, res.length() - 2);
      }
    } finally {
      try {
        if (httpResponse != null) {
          httpResponse.close();
        }
      } catch (IOException e) {
        failed();
        // logger.info("## release resouce error ##" + e);
      }
    }
  }

  /**
   * 生成加密字段
   */
  public String getDigest(String string) {
    if (string == null) {
      return null;
    }
    char hexDigits[] =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
    try {
      MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
      mdInst.update(btInput);
      byte[] md = mdInst.digest();
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (byte byte0 : md) {
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }

  /**
   *
   * @param result 音频字节流
   * @param file 存储路径
   */
  private void byte2File(byte[] result, String file) {
    File audioFile = new File(file);
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(audioFile);
      fos.write(result);

    } catch (Exception e) {
      // logger.info(e.toString());
    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          failed();
          e.printStackTrace();
        }
      }
    }

  }

  public String truncate(String q) {
    if (q == null) {
      return null;
    }
    int len = q.length();
    String result;
    return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
  }
}
