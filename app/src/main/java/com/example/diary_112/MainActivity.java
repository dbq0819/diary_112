package com.example.diary_112;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.diary_112.DiaryAdapter;

public class MainActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextContent;
    private Button buttonSave;
    private RecyclerView recyclerView;
    private DiaryAdapter adapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiaryAdapter(null);
        recyclerView.setAdapter(adapter);

        database = AppDatabase.getInstance(this);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntry();
            }
        });

        loadEntries();
    }

    private void saveEntry() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        long timestamp = System.currentTimeMillis();

        final DiaryEntry entry = new DiaryEntry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setTimestamp(timestamp);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.diaryEntryDao().insert(entry);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                loadEntries();
            }
        }.execute();
    }

    private void loadEntries() {
        new AsyncTask<Void, Void, List<DiaryEntry>>() {
            @Override
            protected List<DiaryEntry> doInBackground(Void... voids) {
                return database.diaryEntryDao().getAllEntries();
            }

            @Override
            protected void onPostExecute(List<DiaryEntry> diaryEntries) {
                super.onPostExecute(diaryEntries);
                adapter = new DiaryAdapter(diaryEntries);
                recyclerView.setAdapter(adapter);
            }
        }.execute();
    }
}