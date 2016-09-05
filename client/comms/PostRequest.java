package comms;

import java.net.*;
import java.io.*;
import java.util.*;

public class PostRequest extends Request {
  private String stringUrl;
  private HashMap<String, String> args;

  public PostRequest(/*Session session,*/ String url) {
    stringUrl = baseUrl + url;
    args = new HashMap<String, String>();
  }

  public PostRequest(/*Session session,*/ String url, HashMap<String, String> args) {
    stringUrl = baseUrl + url;
    this.args = new HashMap<String, String>();
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