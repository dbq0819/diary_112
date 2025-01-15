package com.example.diary_112;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary_entries")
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;
    private long timestamp;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}