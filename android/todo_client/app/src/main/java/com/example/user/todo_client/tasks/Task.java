package com.example.user.todo_client.tasks;

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
    public int categoryId;
    //protected int allocatedExecutiveId;
    //protected int allocatedUserId;
    protected boolean isDeleted;

    public Task () {
        id = 0;
        priority = Priority.LOW;
        status = 0;
        // createdByUserId = 0;
        categoryId = 0;
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
}
