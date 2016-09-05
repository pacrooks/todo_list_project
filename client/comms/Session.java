package comms;

import org.json.*;
import java.util.*;

public class Session {
  private String sessionId;
  private boolean active;

  public Session () {
    sessionId = null;
    active = false;
    GetRequest startSession = new GetRequest("/sessions/login");
    try {
      startSession.sendRequest();
    } catch (Exception e) {
      // Fail silently
    }
    JSONObject jsonSessionId = startSession.receiveJsonObject();
    try {
      sessionId = jsonSessionId.getString("sessionid");
    } catch (Exception e) {
      // Fail silently
      sessionId = null;
    }
  }

  public void login (String userid, String password) {
    if ((sessionId != null) && !active) {
      System.out.println("Attempting to login.");
      HashMap<String, String> args = new HashMap<String, String>();
      args.put("sessionid", sessionId);
      args.put("userid", userid);
      args.put("password", password);
      PostRequest login = new PostRequest("/sessions", args);
      try {
        login.sendRequest();        
      } catch (Exception e) {
        sessionId = null;
      }
      active = ((sessionId != null) && (login.getResponseCode() == 200));
    }
  }

  public void logout () {
    if (active) {
      HashMap<String, String> args = new HashMap<String, String>();
      args.put("sessionid", sessionId);
      PostRequest logout = new PostRequest("/sessions/logout", args);
      try {
        logout.sendRequest();        
      } catch (Exception e) {
        // fail silently
      }
      if (logout.getResponseCode() == 200) {
        active = false;
        sessionId = null;
      }
    }
  }

  public boolean isActive() {
    return active;
  }

  public String getSessionId() {
    return sessionId;
  }

}