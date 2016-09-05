package comms;

import java.net.*;
import java.io.*;

public abstract class Request {
  protected final String baseUrl = "http://localhost:4567";
  protected HttpURLConnection con;
  protected StringBuffer response;

  // public Request() {

  // }

  public abstract void sendRequest() throws Exception;

  protected StringBuffer fillBuffer(HttpURLConnection connection) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuffer stringBuffer = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      stringBuffer.append(inputLine);
    }
    in.close();
    return stringBuffer;
  }

  public String receiveString() {
    return response.toString();
  }

  public void recieveJsonArray() {

  }

  public void receiveJsonItem () {

  }


}