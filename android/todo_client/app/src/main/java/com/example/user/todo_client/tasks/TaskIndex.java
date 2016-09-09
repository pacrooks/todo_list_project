package com.example.user.todo_client.tasks;

import com.example.user.todo_client.categories.Category;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public abstract class TaskIndex {
    protected int[] indices;
    protected ArrayList<Task> index;
    protected String ordering;
    protected boolean reverseOrder;
    protected HashMap<String, String> filter;
    private final String categoryKeyword = "category";

    public TaskIndex() {
        indices = null;
        ordering = "";
        reverseOrder = false;
        filter = new HashMap<String, String>();
        index = new ArrayList<Task>();

    }

    public void setOrdering(String ordering) {
        this.ordering = ordering;
    }

    public void setReverseOrder(boolean reverse) {
        reverseOrder = reverse;
    }

    public void setCategoryFilter(int categoryId) {
        filter.put(categoryKeyword, String.valueOf(categoryId));
    }

    public int length() {
        return index.size();
    }

//    public int getIndex(int i) {
//        if ((indices == null) || (i < 0) || (i >= indices.length)) return 0;
//        return indices[i];
//    }

    public ArrayList<Task> getAll() {
        Task tcopy;
        ArrayList<Task> result = new ArrayList<Task>();
        for (Task t : index) {
            tcopy = t.duplicate();
            if (t.category != null) tcopy.category = t.category.duplicate();
            result.add(tcopy);
        }
        return result;
    }

    public abstract void fetch();
    public abstract void expand();
}
