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

    public Category categoryByName(String name) {
        for (Category cat : index.values()) {
            if (cat.getName().equals(name)) {
                return cat.duplicate();
            }
        }
        return null;
    }

    public Category categorybyId(Integer id) {
        Category result = index.get(id);
        if (result != null) result = result.duplicate();
        return result;
    }

    public ArrayList<Category> getAll() {
        ArrayList<Category> result = new ArrayList<Category>();
        for (Category c : index.values()) {
            if (c != null) result.add(c.duplicate());
        }
        return result;
    }

    public abstract Category getCategory(Integer i);
    public abstract void fetch();
    public abstract void expand();
}
