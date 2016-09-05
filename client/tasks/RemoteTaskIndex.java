package tasks;

import comms.*;
import java.util.*;

public class RemoteTaskIndex extends TaskIndex {

  public RemoteTaskIndex() {
    super();
  }

  public void fetch() {
    String url = "/tasks";
    if (!(ordering.isEmpty() && reverseOrder)) {
      // Create a query string
    }
    GetRequest request = new GetRequest(url);
    try {
      request.sendRequest();
    } catch (Exception e) {
      // Do something
      System.out.println("Exception raised.");
    }
    indices = request.receiveIndexArray();
  }

  public void expand(ArrayList<Task> tasks) {
    for (int index : indices) {
      tasks.add(new RemoteTask(index));
    }
  }
}