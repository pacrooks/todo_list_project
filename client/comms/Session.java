package comms;

import org.json.*;
import java.util.*;

public class Session {
  private static String sessionId = null;
  private static boolean active = false;

  private Session () {
  }

  public static void login (String userid, String password) {
    sessionId = null;
    active = false;
    GetRequest startSession = new GetRequest("/sessions/login", true);
    try {
      startSession.sendRequest();
    } catch (Exception e) {

    }
    JSONObject jsonSessionId = startSession.receiveJsonObject();
    try {
      sessionId = jsonSessionId.getString("sessionid");
    } catch (Exception e) {
      sessionId = null;
    }
    if (sessionId != null) {
      HashMap<String, String> args = new HashMap<String, String>();
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

  public static void logout () {
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

  public static boolean isActive() {
    return active;
  }

  public static String getSessionId() {
    return sessionId;
  }

}