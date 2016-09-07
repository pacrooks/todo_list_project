package com.example.user.todo_client.categories;

import org.json.JSONObject;

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

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("colour", colour);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void fromJson(JSONObject jsonCategory) {
        try {
            id = jsonCategory.getInt("id");
            name = jsonCategory.getString("name");
            colour = jsonCategory.getString("colour");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
