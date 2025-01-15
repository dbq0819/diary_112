package com.example.diary_112;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
                .inflate(R.layout.item_diary, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        DiaryEntry entry = diaryEntries.get(position);
        holder.titleTextView.setText(entry.getTitle());

        // 只显示内容的前两行
        String content = entry.getContent();
        if (content != null && content.length() > 50) { // 假设每行大约25个字符
            content = content.substring(0, 50) + "...";
        }
        holder.contentTextView.setText(content);
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }

    static class DiaryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView; // 新增 CardView 引用
        TextView titleTextView;
        TextView contentTextView;

        DiaryViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView); // 绑定 CardView
            titleTextView = itemView.findViewById(R.id.titleTextView); // 使用自定义 ID
            contentTextView = itemView.findViewById(R.id.contentTextView); // 使用自定义 ID
        }
    }
}