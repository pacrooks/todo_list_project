package com.example.user.todo_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.todo_client.categories.Category;
import com.example.user.todo_client.categories.RemoteCategory;
import com.example.user.todo_client.tasks.RemoteTask;
import com.example.user.todo_client.tasks.Task;

import org.json.JSONObject;

/**
 * Created by user on 06/09/2016.
 */
public class TaskActivity extends AppCompatActivity {
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        try {
            task = new RemoteTask(new JSONObject(bundle.getString("task")));
            task.category = new RemoteCategory(new JSONObject(bundle.getString("category")));
            // now populate the view
            
        } catch (Exception e) {

        }

    }
}
