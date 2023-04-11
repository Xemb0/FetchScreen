package com.example.fetchscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPreferencesActivity extends AppCompatActivity {

    private TextView textView;
    private Map<String, ?> allEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        // Find the "Clear Shared Preferences" button and set an OnClickListener
        Button refreshButton = findViewById(R.id.clear_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Refresh the data
                refreshData();
            }
        });
        // Set OnClickListener on "Open Again" button
        Button openAgainButton = findViewById(R.id.store_button);
        openAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedPreferencesActivity.this, SharedPreferencesActivity.class);
                startActivity(intent);
            }
        });



        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SharedPreferencesEntry> entryList = new ArrayList<>();

        // Get the SharedPreferences object
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Get all key-value pairs from the SharedPreferences
       allEntries = prefs.getAll();

        // Iterate over the key-value pairs and add them to the entryList
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            entryList.add(new SharedPreferencesEntry(entry.getKey(), entry.getValue().toString()));
        }

        // Create and set the adapter for the RecyclerView
        SharedPreferencesAdapter adapter = new SharedPreferencesAdapter(entryList);
        recyclerView.setAdapter(adapter);

    }
    private void refreshData(){
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            builder.append(entry.getKey()).append(":\n").append(entry.getValue().toString()).append("\n\n");
        }
        textView.setText(builder.toString());
    }


}
