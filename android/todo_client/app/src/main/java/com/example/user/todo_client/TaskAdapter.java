package com.example.user.todo_client;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.todo_client.tasks.Task;

import java.util.ArrayList;

/**
 * Created by phil on 14/09/16.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    Context context;
//    int layoutResourceId;
    ArrayList<Task> tasks;

    public TaskAdapter(Context context, int layoutResourceId, ArrayList<Task> tasks) {
        super(context, layoutResourceId, tasks);
//        this.layoutResourceId = layoutResourceId;
//        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View rowView = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (rowView == null) {
//            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listview_item_row, null);
        }

        Task t = tasks.get(position);

        if (t != null) {
            LinearLayout infoLayout = (LinearLayout) rowView.findViewById(R.id.task_info_area_id);
            if (infoLayout != null) {
            }
            TextView priorityView = (TextView) rowView.findViewById(R.id.task_list_priority_id);
            if (priorityView != null) {
                priorityView.setText(t.priority.abbr());
            }
            TextView daysGoneView = (TextView) rowView.findViewById(R.id.task_list_days_gone_id);
            if (daysGoneView != null) {
            }
            TextView daysToGoView = (TextView) rowView.findViewById(R.id.task_list_days_to_go_id);
            if (daysToGoView != null) {
            }
            TextView headlineView = (TextView) rowView.findViewById(R.id.task_list_headline_id);
            if (headlineView != null) {
                headlineView.setText(t.headline);
            }
        }

        // the view must be returned to our activity
        return rowView;

    }
}