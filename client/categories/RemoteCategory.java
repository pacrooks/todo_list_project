package categories;

import comms.*;
import org.json.*;

public class RemoteCategory extends Category {

  public RemoteCategory() {
    super();
  }

  public RemoteCategory(int id) {
    super();
    fetch(id);
  }

  public void fetch(int id) {
    GetRequest fetchRequest = new GetRequest("/categories/" + id);
    try {
      fetchRequest.sendRequest();
      JSONObject jsonCategory = fetchRequest.receiveJsonObject();
      id = jsonCategory.getInt("id");
      name = jsonCategory.getString("name");
      colour = jsonCategory.getString("colour");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}