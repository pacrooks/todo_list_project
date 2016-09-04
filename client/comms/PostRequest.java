package comms;

import java.net.*;
import java.io.*;
import java.util.*;

public class PostRequest extends Request {
  private String stringUrl;
  private int responseCode;
  private StringBuffer response;

  public PostRequest(/*Session session,*/ String url) {
    stringUrl = baseUrl + url;

  }

  public PostRequest(/*Session session,*/ String url, HashMap<String, String> args) {
    stringUrl = baseUrl + url;
  }

  public void addArgs(HashMap<String, String> args) {

  }

  public void sendRequest() throws Exception {
    URL url = new URL(stringUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    //add reuqest header
    con.setRequestMethod("POST");
    // con.setRequestProperty("User-Agent", USER_AGENT);
    // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

    String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

    // Send post request
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    responseCode = con.getResponseCode();
    // System.out.println("\nSending 'POST' request to URL : " + url);
    // System.out.println("Post parameters : " + urlParameters);
    // System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

  }

  public String receiveResponse() {

    return response.toString();

  }

}