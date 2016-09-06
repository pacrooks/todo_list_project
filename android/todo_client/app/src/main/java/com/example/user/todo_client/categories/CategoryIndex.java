package com.example.user.todo_client.categories;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public abstract class CategoryIndex {
    protected HashMap<Integer, Category> index;

    public CategoryIndex() {
        index = new HashMap<Integer, Category>();
    }

    public int length() {
        return index.size();
    }

    public abstract Category getCategory(Integer i);
    public abstract void fetch();
    public abstract void expand();
    public abstract void expand(ArrayList<Category> tasks);
}
