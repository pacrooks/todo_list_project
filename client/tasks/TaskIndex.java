package tasks;

import comms.*;
import java.util.*;

public class TaskIndex {
  private ArrayList<Integer> indices;
  private String ordering;
  private boolean reverseOrder;
  public String response;

  public TaskIndex() {
    indices = new ArrayList<Integer>();
    ordering = "";
    reverseOrder = false;
  }

  public void setOrdering(String ordering) {
    this.ordering = ordering;
  }

  public void setReverseOrder(boolean reverse) {
    reverseOrder = reverse;
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
    response = request.receiveResponse();
  }

// Might need to inject this method somehow
  public void expand(ArrayList<Task> tasks) {
    for (int index : indices) {
      tasks.add(new RemoteTask(index));
    }
  }

}