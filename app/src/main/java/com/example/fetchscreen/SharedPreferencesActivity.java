package com.example.fetchscreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPreferencesActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SharedPreferencesEntry> entryList = new ArrayList<>();

        // Get the SharedPreferences object
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Get all key-value pairs from the SharedPreferences
        Map<String, ?> allEntries = prefs.getAll();

        // Iterate over the key-value pairs and add them to the entryList
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            entryList.add(new SharedPreferencesEntry(entry.getKey(), entry.getValue().toString()));
        }

        // Create and set the adapter for the RecyclerView
        SharedPreferencesAdapter adapter = new SharedPreferencesAdapter(entryList);
        recyclerView.setAdapter(adapter);
    }

}
