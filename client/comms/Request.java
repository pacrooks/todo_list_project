package comms;

import java.net.*;
import java.io.*;
import java.util.*;
import org.json.*;

public abstract class Request {
  protected final String baseUrl = "http://localhost:4567";
  protected HttpURLConnection con;
  protected StringBuffer response;
  protected int responseCode;

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

  protected String makeArgsString(HashMap<String, String> args) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();
    boolean isFirstArg = true;
    for(Map.Entry<String, String> entry : args.entrySet()){
      if (isFirstArg)
        isFirstArg = false;
      else
        result.append("&");

      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }
    return result.toString();
  }

  public int getResponseCode() {
    return responseCode;
  }

  public String receiveString() {
    return response.toString();
  }

  public int[] receiveIndexArray() {
    int[] intArray = null;
    try {
      JSONArray jsonArray = new JSONArray(response.toString());
      intArray = new int[jsonArray.length()];
      for (int i = 0; i < jsonArray.length(); i++) {
        intArray[i] = jsonArray.getInt(i);
      } 
    } catch (Exception e) {

    }
    return intArray;
  }

  public JSONObject receiveJsonObject () {
    JSONObject jsonObject = null;
    try {
      jsonObject = new JSONObject(response.toString());
    } catch (Exception e) {

    }
    return jsonObject;
  }

}