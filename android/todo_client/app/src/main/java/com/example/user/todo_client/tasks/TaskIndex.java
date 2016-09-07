package com.example.user.todo_client.tasks;

import com.example.user.todo_client.categories.Category;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public abstract class TaskIndex {
    protected int[] indices;
    protected String ordering;
    protected boolean reverseOrder;
    protected HashMap<String, String> filter;
    private final String categoryKeyword = "category";

    public TaskIndex() {
        indices = null;
        ordering = "";
        reverseOrder = false;
        filter = new HashMap<String, String>();
    }

    public void setOrdering(String ordering) {
        this.ordering = ordering;
    }

    public void setReverseOrder(boolean reverse) {
        reverseOrder = reverse;
    }

    public void setCategoryFilter(Category category) {
        filter.put(categoryKeyword, String.valueOf(category.getId()));
    }

    public void setCategoryFilter(int categoryId) {
        filter.put(categoryKeyword, String.valueOf(categoryId));
    }

    public int length() {
        if (indices == null) return 0;
        return indices.length;
    }

    public int getIndex(int i) {
        if ((indices == null) || (i < 0) || (i >= indices.length)) return 0;
        return indices[i];
    }

    public abstract void fetch();
    public abstract void expand(ArrayList<Task> tasks);

}
