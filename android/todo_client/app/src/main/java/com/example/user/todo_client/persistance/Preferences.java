package com.example.user.todo_client.persistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 07/09/2016.
 */
public class Preferences {

    public static final String PREF_REVERSE = "reverse";
    public static final String PREF_SORT_BY = "sortBy";
    public static final String PREF_CATEGORY = "category";

    public static void setStoredText(Context context, String key, String text) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public static String getStoredText(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String text = sharedPreferences.getString(key, null);
        return text;
    }
}
