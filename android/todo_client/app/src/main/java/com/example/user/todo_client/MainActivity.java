package com.example.user.todo_client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.todo_client.comms.Session;
import com.example.user.todo_client.tasks.RemoteTaskIndex;
import com.example.user.todo_client.tasks.Task;
import com.example.user.todo_client.tasks.TaskIndex;

import java.util.ArrayList;

/**
 * Created by user on 06/09/2016.
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Task> items;
    private ArrayAdapter<Task> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<Task>();
        itemsAdapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        new DownloadTasks().execute(items);


//        items.add("First Item");
//        items.add("Second Item");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private class DownloadTasks extends AsyncTask<ArrayList<Task>, Integer, Integer> {
        protected Integer doInBackground(ArrayList<Task>... buffers) {

            if (!Session.isActive()) {
                Session.login("matt", "matt");
            }

            TaskIndex ti = new RemoteTaskIndex();

            for (ArrayList<Task> buffer : buffers) {
                ti.fetch();
                ti.expand(buffer);
            }
            return ti.length();
        }

        protected void onProgressUpdate(Integer... progress) {};

        protected void onPostExecute(Integer result) {};
    }
}
