package com.example.user.todo_client.tasks;

import com.example.user.todo_client.comms.GetRequest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public class RemoteTaskIndex extends TaskIndex {
    private final String orderKeyword = "order";
    private final String reverseKeyword = "reverse";

    public RemoteTaskIndex() {
        super();
    }

    public void fetch() {
        String url = "/tasks";
//        ordering = "headline";
//        setCategoryFilter(164);
//        reverseOrder = true;
        HashMap<String, String> requestArgs = new HashMap<>();
        requestArgs.putAll(filter);
        if (!ordering.isEmpty()) {
            requestArgs.put(orderKeyword, ordering);
        }
        if (reverseOrder) {
            requestArgs.put(reverseKeyword, String.valueOf(1));
        }
        GetRequest request = new GetRequest(url);
        if (!requestArgs.isEmpty()) request.addArgs(requestArgs);
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