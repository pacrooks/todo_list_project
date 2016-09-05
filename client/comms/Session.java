package comms;

import org.json.*;

public class Session {
  private String sessionId = "";
  private boolean active = false;

  public Session (String userid, String password) {
    GetRequest startSession = new GetRequest("/sessions/login");
    try {
      startSession.sendRequest();
    } catch (Exception e) {

    }
    JSONObject jsonSessionId = startSession.receiveJsonObject();
    try {
      sessionId = jsonSessionId.getString("sessionid");
    } catch (Exception e) {
      // Fail silently
      sessionId = null;
    }
    if (!sessionId.isEmpty()) {
      //PostRequest login = new PostRequest( );
    }

  }

  public boolean isActive() {
    return active;
  }

  public String getSessionId() {
    return sessionId;
  }

}