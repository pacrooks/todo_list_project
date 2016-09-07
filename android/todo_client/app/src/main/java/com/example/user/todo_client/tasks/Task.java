package com.example.user.todo_client.tasks;

import com.example.user.todo_client.categories.Category;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 06/09/2016.
 */
public abstract class Task {

    protected int id;
    public String headline;
    public String description;
    public Date createDate;
    public Date targetDate;
    public Priority priority;
    public int status;
    //protected int createdByUserId;
    //protected int allocatedExecutiveId;
    //protected int allocatedUserId;
    protected boolean isDeleted;
    public int categoryId;
    public Category category;

    public Task () {
        id = 0;
        priority = Priority.LOW;
        status = 0;
        // createdByUserId = 0;
        categoryId = 0;
        category = null;
        // allocatedUserId = 0;
        // allocatedExecutiveId = 0;
        headline = "";
        description = "";
        isDeleted = false;
    }

    public abstract void fetch( int id );
    public abstract int save();
    public abstract void update();
    public abstract void delete();

    public int getId() {
        return id;
    }

    public String toString() {
        return headline;
    }

    protected Date stringToDate(String stringDate) {
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

    protected String dateToString(Date date) {
        if (date == null) return "null";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public JSONObject toJson() {
        JSONObject jsonTask = new JSONObject();
        try {
            jsonTask.put("id", String.valueOf(id));
            jsonTask.put("headline", headline);
            jsonTask.put("description", description);
            jsonTask.put("create_date", dateToString(createDate));
            jsonTask.put("target_date", dateToString(targetDate));
            jsonTask.put("priority", priority);
            jsonTask.put("status", status);
            // jsonTask.put("created_by_user_id", createdByUserId);
            jsonTask.put("category_id", categoryId);
            // jsonTask.put("allocated_executive_id", allocatedExecutiveId);
            // jsonTask.put("allocated_user_id", allocatedUserId );
            jsonTask.put("is_deleted", isDeleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonTask;
    }

    protected void fromJson(JSONObject jsonTask) {
        try {
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
}
