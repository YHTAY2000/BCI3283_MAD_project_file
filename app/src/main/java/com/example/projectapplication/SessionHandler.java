package com.example.projectapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionHandler {
    private static final String SESSION_PREF_NAME = "SessionPref";
    private static final String KEY_EVENT_NAME = "eventName";

    // Add more keys for other session data as needed

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionHandler(Context context) {
        sharedPreferences = context.getSharedPreferences(SESSION_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String event_name) {
        editor.putString(KEY_EVENT_NAME, event_name);
        editor.apply();
    }

    public String getEventName() {
        return sharedPreferences.getString(KEY_EVENT_NAME, null);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }

}

