import org.apache.commons.lang3.StringEscapeUtils;

public class state {
  public int tid;
  public String signalstring;
  public String deletestring;
  public String containstring;
  public int[] p = {0, 0, 0, 0, 0};

  public state(int tid, String signalstring, String deletestring, String containstring, int[] p) {
    this.tid = tid;
    this.signalstring = StringEscapeUtils.unescapeJava(signalstring);
    this.deletestring = StringEscapeUtils.unescapeJava(deletestring);
    this.p = p;
    this.containstring = StringEscapeUtils.unescapeJava(containstring);
    String[] c = {"*", "+", "[", "]", "{", "}", "(", ")", "."};
    for (String cc : c) {
      this.signalstring = this.signalstring.replaceAll("\\" + cc, "\\\\" + cc);
      this.deletestring = this.deletestring.replaceAll("\\" + cc, "\\\\" + cc);
      this.containstring = this.containstring.replaceAll("\\" + cc, "\\\\" + cc);
      this.deletestring = this.deletestring.replaceAll("\\" + cc, "\\\\" + cc);
    }
    this.deletestring = "[" + this.deletestring + "]";
    // System.out.println(this.deletestring);
  }
}
