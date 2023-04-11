package com.example.fetchscreen;

import android.accessibilityservice.AccessibilityService;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        traverseNode(rootNode, builder);
        String text = builder.toString().trim();
        if (!text.isEmpty()) {
            // Convert package name to app name
            PackageManager pm = getPackageManager();
            String packageName = event.getPackageName().toString();
            String appName = packageName;
            try {
                appName = pm.getApplicationLabel(pm.getApplicationInfo(packageName, 0)).toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            // Store the text data in SharedPreferences
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(appName, text);
            editor.apply();

        }
    }



    private void traverseNode(AccessibilityNodeInfo node, StringBuilder builder) {
        if (node.getChildCount() == 0) {
            CharSequence text = node.getText();
            if (text != null && !TextUtils.isEmpty(text)) {
                builder.append(text).append("\n");
            }
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                AccessibilityNodeInfo child = node.getChild(i);
                if (child != null) {
                    traverseNode(child, builder);
                    child.recycle();
                }
            }
        }
    }


    @Override
    public void onInterrupt() {
        // Do nothing
    }
}
