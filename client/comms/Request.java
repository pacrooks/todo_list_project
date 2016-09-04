package comms;

public abstract class Request {
  protected final String baseUrl = "http://localhost:4567";

  // public Request() {

  // }

  public abstract void sendRequest() throws Exception;
  public abstract String receiveResponse();


}