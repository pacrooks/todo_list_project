public class Restful {

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
    
  public static void issueHttpGet(String url)
  {
    HttpClient httpclient = new DefaultHttpClient();

    // Prepare a request object
    HttpGet httpGet = new HttpGet(url); 

    // Execute the request
    HttpResponse response;
    try {
      response = httpclient.execute(httpGet);
      // Examine the response status
      Log.i("Praeda",response.getStatusLine().toString());

      // Get hold of the response entity
      HttpEntity entity = response.getEntity();
      // If the response does not enclose an entity, there is no need
      // to worry about connection release

      if (entity != null) {
        // A Simple JSON Response Read
        InputStream instream = entity.getContent();
        String result= convertStreamToString(instream);
        // now you have the string representation of the HTML request
        instream.close();
      }
    } catch (Exception e) {}
  }


  public static String issueHttpPost(String url,
              HashMap<String, String> postParameters) throws UnsupportedEncodingException {
          // TODO Auto-generated method stub

    HttpClient client = getNewHttpClient();

    try{
      request = new HttpPost(url);
    }
    catch(Exception e){
      e.printStackTrace();
    }

    if(postParameters!=null && postParameters.isEmpty()==false) {
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(postParameters.size());
      String k, v;
      Iterator<String> itKeys = postParameters.keySet().iterator();
      while (itKeys.hasNext()) {
        k = itKeys.next();
        v = postParameters.get(k);
        nameValuePairs.add(new BasicNameValuePair(k, v));
      }     
      UrlEncodedFormEntity urlEntity  = new UrlEncodedFormEntity(nameValuePairs);
      request.setEntity(urlEntity);
    }

    try {
      Response = client.execute(request,localContext);
      HttpEntity entity = Response.getEntity();
      int statusCode = Response.getStatusLine().getStatusCode();
      Log.i(TAG, ""+statusCode);
      Log.i(TAG, "------------------------------------------------");
      try{
        InputStream in = (InputStream) entity.getContent(); 
        //Header contentEncoding = Response.getFirstHeader("Content-Encoding");
        /*if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
        in = new GZIPInputStream(in);
        }*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null) {
          str.append(line + "\n");
        }
        in.close();
        response = str.toString();
        Log.i(TAG, "response"+response);
      } catch(IllegalStateException exc) {
        exc.printStackTrace();
      }
    } catch(Exception e){
      Log.e("log_tag", "Error in http connection "+response);
    }
    return response;
  }

}