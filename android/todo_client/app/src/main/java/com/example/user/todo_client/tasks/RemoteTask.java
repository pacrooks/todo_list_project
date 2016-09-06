package com.example.user.todo_client.tasks;

import com.example.user.todo_client.comms.GetRequest;
import com.example.user.todo_client.comms.PostRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
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

    private String dateToString(Date date) {
        if (date == null) return "null";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
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
            id = jsonTask.getInt("id");
            headline = jsonTask.getString("headline");
            description = jsonTask.getString("description");
            createDate = stringToDate(jsonTask.getString("create_date"));
            createDate = stringToDate(jsonTask.getString("target_date"));
            priority = Priority.values()[jsonTask.getInt("priority")];
            status = jsonTask.getInt("status");
            // createdByUserId = jsonTask.getInt("created_by_user_id");
            categoryId = jsonTask.getInt("category_id");
            // allocatedExecutiveId = jsonTask.getInt("allocated_executive_id");
            // allocatedUserId = jsonTask.getInt("allocated_user_id");
            isDeleted = jsonTask.getBoolean("is_deleted");
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
}
