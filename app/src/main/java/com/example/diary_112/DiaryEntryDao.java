package com.example.diary_112;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaryEntryDao {
    @Insert
    void insert(DiaryEntry diaryEntry);

    @Query("SELECT * FROM diary_entries ORDER BY timestamp DESC")
    List<DiaryEntry> getAllEntries();

    @Query("SELECT * FROM diary_entries WHERE id = :id")
    DiaryEntry getEntryById(int id);

    @Update
    void update(DiaryEntry diaryEntry);

    @Delete
    void delete(DiaryEntry diaryEntry); // 添加删除方法
}