package com.example.fetchscreen;

public class SharedPreferencesEntry {
    private String key;
    private String value;

    public SharedPreferencesEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

