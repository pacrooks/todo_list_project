package com.example.user.todo_client.comms;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
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
        URL url = new URL(stringUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        response = fillBuffer(con);
    }

}