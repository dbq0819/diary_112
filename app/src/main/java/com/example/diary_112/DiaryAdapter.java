package com.example.diary_112;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private List<DiaryEntry> diaryEntries;

    public DiaryAdapter(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        DiaryEntry entry = diaryEntries.get(position);
        holder.titleTextView.setText(entry.getTitle());
        holder.contentTextView.setText(entry.getContent());
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }

    static class DiaryViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;

        DiaryViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(android.R.id.text1);
            contentTextView = itemView.findViewById(android.R.id.text2);
        }
    }
}