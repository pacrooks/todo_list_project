package com.example.user.todo_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.user.todo_client.categories.RemoteCategory;
import com.example.user.todo_client.tasks.RemoteTask;
import com.example.user.todo_client.tasks.Task;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 06/09/2016.
 */
public class TaskActivity extends AppCompatActivity {
    Task task;

    private String dateToString(Date date) {
        if (date == null) return "01/01/2000";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        try {
            task = new RemoteTask(new JSONObject(bundle.getString("task")));
            task.category = new RemoteCategory(new JSONObject(bundle.getString("category")));
            ((EditText)findViewById(R.id.task_headline_id)).setText(task.headline);
            ((EditText)findViewById(R.id.task_details_id)).setText(task.description);
            ((EditText)findViewById(R.id.task_priority_id)).setText(task.priority.toString());
            ((EditText)findViewById(R.id.task_category_id)).setText(task.category.name);
            ((EditText)findViewById(R.id.task_target_date_id)).setText(dateToString(task.targetDate));

        } catch (Exception e) {

        }

    }
}
