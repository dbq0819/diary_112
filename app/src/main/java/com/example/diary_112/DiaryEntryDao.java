package com.example.diary_112;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiaryEntryDao {
    @Insert
    void insert(DiaryEntry diaryEntry);

    @Query("SELECT * FROM diary_entries ORDER BY timestamp DESC")
    List<DiaryEntry> getAllEntries();
}