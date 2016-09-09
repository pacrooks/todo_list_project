package com.example.user.todo_client.tasks;

import com.example.user.todo_client.categories.Category;
import com.example.user.todo_client.comms.GetRequest;
import com.example.user.todo_client.comms.PostRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public class RemoteTask extends Task {
    public RemoteTask() {
        super();
    }

    public RemoteTask(int id) {
        super();
        fetch(id);
    }

    public RemoteTask(JSONObject jsonObject) {
        super();
        fromJson(jsonObject);
    }

    private RemoteTask(Task another) {
        id = another.id;
        headline = another.headline;
        description = another.description;
        createDate = another.createDate;
        targetDate = another.targetDate;
        priority = another.priority;
        status = another.status;
        isDeleted = another.isDeleted;
        categoryId = another.categoryId;
        category = another.category;
    }

    private HashMap<String, String> buildArgList() {
        HashMap<String, String> argList = new HashMap<String, String>();
        argList.put("id", String.valueOf(id));
        argList.put("headline", headline);
        argList.put("description", description);
        argList.put("create_date", dateToString(createDate));
        argList.put("target_date", dateToString(targetDate));
        argList.put("priority", String.valueOf(priority));
        argList.put("status", String.valueOf(status));
        // argList.put("created_by_user_id", createdByUserId);
        argList.put("category_id", String.valueOf(categoryId));
        // argList.put("allocated_executive_id", allocatedExecutiveId);
        // argList.put("allocated_user_id", allocatedUserId );
        argList.put("is_deleted", String.valueOf(isDeleted));
        return argList;
    }

    public void fetch(int id) {
        GetRequest fetchRequest = new GetRequest("/tasks/" + id);
        try {
            fetchRequest.sendRequest();
            JSONObject jsonTask = fetchRequest.receiveJsonObject();
            fromJson(jsonTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int save() {
        HashMap<String, String> args = buildArgList();
        PostRequest saveRequest = new PostRequest("/tasks", args);
        try {
            saveRequest.sendRequest();
            id = saveRequest.receiveJsonObject().getInt("id");
        } catch (Exception e) {
            id = 0;
        }
        return id;
    }

    public void update() {
        HashMap<String, String> args = buildArgList();
        PostRequest updateRequest = new PostRequest("/tasks/" + id, args);
        try {
            updateRequest.sendRequest();
        } catch (Exception e) {

        }
    }

    public void delete() {
        PostRequest deleteRequest = new PostRequest("/tasks/" + id + "/delete");
        try {
            deleteRequest.sendRequest();
        } catch (Exception e) {
            // Fail quietly
        }
        if (deleteRequest.getResponseCode() == 200) {
            // Item deleted
            id = 0;
        }
    }

    public Task duplicate() {
        return new RemoteTask(this);
    }
}
