package comms;

import java.net.*;
import java.io.*;
import java.util.*;

public class GetRequest extends Request {
  private String stringUrl;
  private int responseCode;
  private StringBuffer response;

  public GetRequest (/*Session session,*/ String url) {
    stringUrl = baseUrl + url;
    // need to get the session across somehow

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

    responseCode = con.getResponseCode();
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
  }

  public String receiveResponse() {
    return response.toString();
  }
}