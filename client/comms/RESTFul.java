package comms;
import java.net.*;
import java.io.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

public class RESTful {

  private static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }
    
  public static void httpGet(String stringUrl)
  {
    String result = "";
    URL url = new URL(stringUrl);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
      InputStream in = new BufferedInputStream(urlConnection.getInputStream());
      result = convertStreamToString(in);
    } finally {
      urlConnection.disconnect();
    }
  }


  public static String issueHttpPost(String stringUrl, HashMap<String, String> postParameters) {
    URL url = new URL(stringUrl);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setDoOutput(true);
    // urlConnection.setRequestProperty("Accept-Charset", charset);
    // urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset
    try {
      OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
      writeStream(out);

      InputStream in = new BufferedInputStream(urlConnection.getInputStream());
      // InputStream response = connection.getInputStream();
      // readStream(in);
    } finally {
      urlConnection.disconnect();
    }

  }

}

