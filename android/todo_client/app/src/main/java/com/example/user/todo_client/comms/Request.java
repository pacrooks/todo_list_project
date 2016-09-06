package com.example.user.todo_client.comms;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 06/09/2016.
 */
public abstract class Request {
    protected final String baseUrl = "http://10.86.202.54:4567";
    protected HttpURLConnection con;
    protected StringBuffer response;
    protected int responseCode;
    protected HashMap<String, String> args;
    protected String stringUrl;

    public Request(String url) {
        stringUrl = baseUrl + url;
        args = new HashMap<String, String>();
    }

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
