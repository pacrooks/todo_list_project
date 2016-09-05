package tasks;

import comms.*;
import org.json.*;
import java.util.*;
import java.text.*;

public class RemoteTask extends Task {
  public RemoteTask() {
    super();
  }

  public RemoteTask(int id) {
    super();
    fetch(id);
  }

  private Date stringToDate(String stringDate) {
    if (stringDate == "null") return null;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    Date date = new Date((long)0);
    try {
      date = df.parse(stringDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public void fetch(int id) {
    GetRequest fetchRequest = new GetRequest("/tasks/" + id);
    try {
      fetchRequest.sendRequest();
      JSONObject jsonTask = fetchRequest.receiveJsonObject();
      id = jsonTask.getInt("id");
      headline = jsonTask.getString("headline");
      description = jsonTask.getString("description");
      createDate = stringToDate(jsonTask.getString("create_date"));
      targetDate = stringToDate(jsonTask.getString("target_date"));
      priority = jsonTask.getInt("priority");
      status = jsonTask.getInt("status");
      createdByUserId = jsonTask.getInt("created_by_user_id");
      categoryId = jsonTask.getInt("category_id");
      allocatedExecutiveId = jsonTask.getInt("allocated_executive_id");
      allocatedUserId = jsonTask.getInt("allocated_user_id");
      isDeleted = jsonTask.getBoolean("is_deleted");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public int save() {

    return 0;

  }

  public void update() {

  }

  public void delete() {

  }
}