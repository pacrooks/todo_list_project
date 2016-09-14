package com.example.user.todo_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.todo_client.categories.Category;
import com.example.user.todo_client.categories.CategoryIndex;
import com.example.user.todo_client.categories.LocalCategoryIndex;
import com.example.user.todo_client.categories.RemoteCategoryIndex;
import com.example.user.todo_client.comms.Session;
import com.example.user.todo_client.persistance.Preferences;
import com.example.user.todo_client.tasks.RemoteTaskIndex;
import com.example.user.todo_client.tasks.Task;
import com.example.user.todo_client.tasks.TaskIndex;

import java.util.ArrayList;

/**
 * Created by user on 06/09/2016.
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Task> items;
//    private ArrayAdapter<Task> itemsAdapter;
    private TaskAdapter itemsAdapter;
    private ListView lvItems;
    private CategoryIndex lcatIndex = null;
    private class ResultSet {
        public ArrayList<Task> taskList;
        public ArrayList<Category> catList;
        public ResultSet() {
            taskList = new ArrayList<>();
            catList = new ArrayList<>();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String categoryFilter = Preferences.getStoredText(this, Preferences.PREF_CATEGORY);
        if (categoryFilter == null) Preferences.setStoredText(this, Preferences.PREF_CATEGORY, String.valueOf(0));
        String reverse = Preferences.getStoredText(this, Preferences.PREF_REVERSE);
        if (reverse == null) Preferences.setStoredText(this, Preferences.PREF_REVERSE, Boolean.toString(false));
        String sortBy = Preferences.getStoredText(this, Preferences.PREF_SORT_BY);
        if (sortBy == null) Preferences.setStoredText(this, Preferences.PREF_SORT_BY, "priority");

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<Task>();
//        itemsAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, items);
        itemsAdapter = new TaskAdapter(this, R.layout.listview_item_row, items);
//        itemsAdapter.setNotifyOnChange(false);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

        new DownloadTasks().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_reverse_id:
                // Update the persistence data then redo the download
                String reverse = Preferences.getStoredText(MainActivity.this, Preferences.PREF_REVERSE);
                Boolean bool = !Boolean.valueOf(reverse);
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, bool.toString());
                new DownloadTasks().execute();
                return true;
            case R.id.menu_refresh_id:
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_priority_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_SORT_BY, "priority");
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_headline_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_SORT_BY, "headline");
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_due_date_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_SORT_BY, "target_date");
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_family_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_CATEGORY, "family");
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_films_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_CATEGORY, String.valueOf(lcatIndex.categoryByName("films").getId()));
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_friends_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_CATEGORY, String.valueOf(lcatIndex.categoryByName("friends").getId()));
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_music_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_CATEGORY, String.valueOf(lcatIndex.categoryByName("music").getId()));
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_work_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_CATEGORY, String.valueOf(lcatIndex.categoryByName("work").getId()));
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            case R.id.submenu_all_id:
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_CATEGORY, String.valueOf(0));
                Preferences.setStoredText(MainActivity.this, Preferences.PREF_REVERSE, Boolean.toString(false));
                new DownloadTasks().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        // Skip to the edit view
                        Intent intent = new Intent(item.getContext(), TaskActivity.class);
                        intent.putExtra("task", items.get(pos).toJson().toString());
                        intent.putExtra("category", items.get(pos).category.toJson().toString());
                        startActivity(intent);
                    }
                });
    }

    private class DownloadTasks extends AsyncTask<Void, Void, ResultSet> {
        protected ResultSet doInBackground(Void... params) {
            ResultSet results = new ResultSet();

            if (!Session.isActive()) {
                Session.login("phil", "phil");
            }


            CategoryIndex ci = new RemoteCategoryIndex();
            ci.fetch();

            TaskIndex ti = new RemoteTaskIndex();
            ti.setReverseOrder(Boolean.valueOf(Preferences.getStoredText(MainActivity.this, Preferences.PREF_REVERSE)));
            ti.setOrdering(Preferences.getStoredText(MainActivity.this, Preferences.PREF_SORT_BY));
            Integer catFilter = Integer.valueOf(Preferences.getStoredText(MainActivity.this, Preferences.PREF_CATEGORY));
            if (catFilter > 0) ti.setCategoryFilter(catFilter);
            ti.fetch();
            ti.expand();
            results.taskList.addAll(ti.getAll());
            for (Task t : results.taskList) {
                t.category = ci.getCategory(t.categoryId);
            }
            results.catList.addAll(ci.getAll());
            return results;
        }

        protected void onProgressUpdate(Integer... progress) {};

        protected void onPostExecute(ResultSet download) {
            lcatIndex = new LocalCategoryIndex(download.catList);
            items.clear();
            items.addAll(download.taskList);
//            itemsAdapter.addAll(download.taskList);
            // Need to ask the view to update
            itemsAdapter.notifyDataSetChanged();
        };
    }
}
