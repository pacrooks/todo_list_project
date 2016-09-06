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
