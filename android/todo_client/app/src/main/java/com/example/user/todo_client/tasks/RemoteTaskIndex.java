package com.example.user.todo_client.tasks;

import com.example.user.todo_client.comms.GetRequest;

import java.util.ArrayList;

/**
 * Created by user on 06/09/2016.
 */
public class RemoteTaskIndex extends TaskIndex {

    public RemoteTaskIndex() {
        super();
    }

    public void fetch() {
        String url = "/tasks";
        if (!(ordering.isEmpty() && reverseOrder)) {
            // Create a query string
        }
        GetRequest request = new GetRequest(url);
        try {
            request.sendRequest();
        } catch (Exception e) {
            // Do something
            System.out.println("Exception raised.");
        }
        indices = request.receiveIndexArray();
    }

    public void expand(ArrayList<Task> tasks) {
        for (int index : indices) {
            tasks.add(new RemoteTask(index));
        }
    }
}