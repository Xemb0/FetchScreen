package com.example.fetchscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SharedPreferencesAdapter extends RecyclerView.Adapter<SharedPreferencesAdapter.ViewHolder> {

    private List<SharedPreferencesEntry> entryList;

    public SharedPreferencesAdapter(List<SharedPreferencesEntry> entryList) {
        this.entryList = entryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView keyTextView;
        public TextView valueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            keyTextView = itemView.findViewById(R.id.key_text_view);
            valueTextView = itemView.findViewById(R.id.value_text_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shared_preferences_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SharedPreferencesEntry entry = entryList.get(position);
        holder.keyTextView.setText(entry.getKey());
        holder.valueTextView.setText(entry.getValue());
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
