public class RemoteCategory extends Category {

  public void fetch(int id) {
    GetRequest fetchRequest = new GetRequest("/categories/" + id);
    try {
      fetchRequest.sendRequest();
      JSONObject jsonTask = fetchRequest.receiveJsonObject();
      id = jsonTask.getInt("id");
      name = jsonTask.getString("name");
      colour = sonTask.getString("colour");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}