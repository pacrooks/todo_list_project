package categories;

import comms.*;
import java.util.*;

public class RemoteCategoryIndex extends CategoryIndex {

  public RemoteCategoryIndex() {
    super();
  }

  public Category getCategory(Integer i) {
    RemoteCategory cat = (RemoteCategory)index.get(i);
    if (cat != null) return cat;
    cat = new RemoteCategory(i);
    if (!cat.isEmpty()) {
      index.put(i, cat);
      return cat;
    }
    return null;
  }

  public void fetch() {
    String url = "/categories";
    GetRequest fetchRequest = new GetRequest(url);
    try {
      fetchRequest.sendRequest();
    } catch (Exception e) {
      // Do something
    }
    int[] indices = fetchRequest.receiveIndexArray();
    for (int i = 0; i < indices.length; i++) {
      index.put(indices[i], null);
    }
  }

  public void expand() {
    for (int i : index.keySet()) {
      index.put(i, new RemoteCategory(i));
    }
  }

  public void expand(ArrayList<Category> categories) {
    for (int i : index.keySet()) {
      categories.add(index.get(i));
    }
  }
}