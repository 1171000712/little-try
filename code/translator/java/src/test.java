import java.io.UnsupportedEncodingException;
import com.baidu.translate.demo.TransApi;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class test {
  private String APP_ID;
  private String SECURITY_KEY;
  private int tid;
  public String ans = "";

  public String getans() {
    return ans;
  }

  public String trans(String s) throws UnsupportedEncodingException {
    TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    String query = s;
    String str = api.getTransResult(query, "auto", "zh");
    JsonObject jsonObj = (JsonObject) new JsonParser().parse(str);
    // System.out.print(str);
    String res = jsonObj.get("trans_result").toString();
    JsonArray js = new JsonParser().parse(res).getAsJsonArray();
    String anString = (((JsonObject) js.get(0)).get("dst").getAsString());
    return (anString);
  }

  public test(String app, String key, int tid) {
    this.APP_ID = app.trim();
    this.SECURITY_KEY = key.trim();
    this.tid = tid;
    if (tid == 1) {
      try {
        ans = trans("I am a pig.");
      } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        // e.printStackTrace();
      }
    }


  }

}
