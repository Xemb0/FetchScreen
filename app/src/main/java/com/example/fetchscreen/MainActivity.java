package com.example.fetchscreen;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewFirebase;
    private static final String PREF_ACCESSIBILITY_SERVICE_ENABLED = "accessibility_service_enabled";
    private boolean isServiceEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the accessibility service is enabled
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);

        for (AccessibilityServiceInfo service : enabledServices) {
            if (service.getId().equals(new ComponentName(this, MyAccessibilityService.class).toString())) {
                isServiceEnabled = true;
                break;
            }
        }

        if (!isServiceEnabled) {
            // Check if we have already prompted the user to enable the service
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean alreadyPrompted = prefs.getBoolean(PREF_ACCESSIBILITY_SERVICE_ENABLED, false);

            if (!alreadyPrompted) {
                // Prompt the user to enable the service
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                Toast.makeText(this, "Please enable the MyAccessibilityService to retrieve text data", Toast.LENGTH_LONG).show();

                // Save the fact that we have prompted the user
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(PREF_ACCESSIBILITY_SERVICE_ENABLED, true);
                editor.apply();
            }
        }


        textViewFirebase = findViewById(R.id.text_view_firebase);
        TextView textViewSharedPreferences = findViewById(R.id.text_view_shared_preferences);


        // Read data from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String sharedPrefsText = prefs.getString(getPackageName(), "");
        textViewSharedPreferences.setText(sharedPrefsText);
        // Set OnClickListener on "View Shared Preferences" button
        Button viewSharedPrefsButton = findViewById(R.id.btn_view_shared_prefs);
        viewSharedPrefsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SharedPreferencesActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start accessibility service
        Intent intent = new Intent(this, MyAccessibilityService.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop accessibility service
        Intent intent = new Intent(this, MyAccessibilityService.class);
        stopService(intent);
    }
}
