package com.example.diary_112;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private List<DiaryEntry> diaryEntries;
    private OnItemClickListener listener;
    private OnItemDeleteListener deleteListener; // 添加删除监听器

    public DiaryAdapter(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
    }

    public interface OnItemClickListener {
        void onItemClick(DiaryEntry diaryEntry);
    }

    public interface OnItemDeleteListener {
        void onItemDelete(DiaryEntry diaryEntry); // 定义删除监听器接口
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener deleteListener) {
        this.deleteListener = deleteListener; // 设置删除监听器
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

        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String dateString = sdf.format(new Date(entry.getTimestamp()));
        holder.dateTextView.setText(dateString); // 显示日期

        // 设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(entry);
                }
            }
        });

        // 设置删除按钮点击事件
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onItemDelete(entry);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }

    static class DiaryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleTextView;
        TextView contentTextView;
        TextView dateTextView; // 添加日期 TextView
        Button deleteButton;

        DiaryViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView); // 绑定日期 TextView
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}