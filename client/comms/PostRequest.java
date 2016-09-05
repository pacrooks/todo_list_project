package comms;

import java.net.*;
import java.io.*;
import java.util.*;

public class PostRequest extends Request {

  public PostRequest(String url) {
    super(url);
    args.put("sessionid", Session.getSessionId());
  }

  public PostRequest(String url, HashMap<String, String> args) {
    super(url);
    stringUrl = baseUrl + url;
    args.put("sessionid", Session.getSessionId());
    this.args.putAll(args);
  }

  public void addArgs(HashMap<String, String> args) {
    this.args.putAll(args);
  }

  public void sendRequest() throws Exception {
    URL url = new URL(stringUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    //add reuqest header
    con.setRequestMethod("POST");
    // con.setRequestProperty("User-Agent", USER_AGENT);
    // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

    String urlParameters = makeArgsString(args);

    // Send post request
    con.setDoInput(true);
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    responseCode = con.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Post parameters : " + urlParameters);
    System.out.println("Response Code : " + responseCode);

    response = fillBuffer(con);
  }


}