package com.example.user.todo_client.categories;

import java.util.ArrayList;

/**
 * Created by user on 09/09/2016.
 */
public class LocalCategoryIndex extends CategoryIndex {
    public LocalCategoryIndex (ArrayList<Category> categories) {
        super();
        for (Category cat : categories) {
            index.put(cat.id, cat);
        }
    }

    public Category getCategory(Integer i) {
        return (index.get(i).duplicate());
    };

    public void fetch() {
        // Empty until local cache created
    };

    public void expand() {
        // Empty until local cache created
    };

}
