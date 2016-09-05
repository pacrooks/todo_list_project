package comms;

import java.net.*;
import java.util.*;

public class GetRequest extends Request {

  public GetRequest (String url) {
    super(url);
    args.put("sessionid", Session.getSessionId());
  }

  public GetRequest (String url, boolean ommitSession) {
    super(url);
    if (!ommitSession) {
      args.put("sessionid", Session.getSessionId());
    }
  }

  public void addArgs(HashMap<String, String> args) {
    this.args.putAll(args);
  }

  public void sendRequest() throws Exception {
    System.out.println("String URL: " + stringUrl);
    URL url = new URL(stringUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    // optional default is GET
    con.setRequestMethod("GET");

    //add request header
    //con.setRequestProperty("User-Agent", USER_AGENT);

    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);

    response = fillBuffer(con);
  }

}