package com.example.user.todo_client.categories;

import com.example.user.todo_client.comms.GetRequest;

import org.json.JSONObject;

/**
 * Created by user on 06/09/2016.
 */
public class RemoteCategory extends Category {

    public RemoteCategory() {
        super();
    }

    public RemoteCategory(int id) {
        super();
        fetch(id);
    }

    private RemoteCategory(Category another) {
        id = another.id;
        name = another.name;
        colour = another.colour;
    }

    public RemoteCategory(JSONObject jsonObject) {
        super();
        fromJson(jsonObject);
    }

    public Category duplicate() {
        return new RemoteCategory(this);
    }

    public void fetch(int id) {
        GetRequest fetchRequest = new GetRequest("/categories/" + id);
        try {
            fetchRequest.sendRequest();
            JSONObject jsonCategory = fetchRequest.receiveJsonObject();
            fromJson(jsonCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
