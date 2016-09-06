package com.example.user.todo_client.categories;

/**
 * Created by user on 06/09/2016.
 */
public abstract class Category {
    protected int id;
    public String name;
    public String colour;

    public Category() {
        id = 0;
        name = null;
        colour = null;
    }

    public boolean isEmpty() {
        return (name == null);
    }

    public abstract void fetch( int id );
    // public abstract int save();
    // public abstract void update();
    // public abstract void delete();
}
