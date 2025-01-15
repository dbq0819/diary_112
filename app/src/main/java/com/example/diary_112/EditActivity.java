package com.example.diary_112;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextContent;
    private Button buttonSave;
    private AppDatabase database;
    private DiaryEntry diaryEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);

        database = AppDatabase.getInstance(this);

        // 检查是否有传递过来的日记项 ID
        int diaryEntryId = getIntent().getIntExtra("DIARY_ENTRY_ID", -1);
        if (diaryEntryId != -1) {
            // 加载现有的日记项
            new AsyncTask<Void, Void, DiaryEntry>() {
                @Override
                protected DiaryEntry doInBackground(Void... voids) {
                    return database.diaryEntryDao().getEntryById(diaryEntryId);
                }

                @Override
                protected void onPostExecute(DiaryEntry entry) {
                    super.onPostExecute(entry);
                    diaryEntry = entry;
                    editTextTitle.setText(entry.getTitle());
                    editTextContent.setText(entry.getContent());
                }
            }.execute();
        } else {
            diaryEntry = new DiaryEntry();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntry();
            }
        });
    }

    private void saveEntry() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        long timestamp = System.currentTimeMillis();

        diaryEntry.setTitle(title);
        diaryEntry.setContent(content);
        diaryEntry.setTimestamp(timestamp);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (diaryEntry.getId() == 0) {
                    database.diaryEntryDao().insert(diaryEntry);
                } else {
                    database.diaryEntryDao().update(diaryEntry);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish(); // 保存后关闭编辑页面
            }
        }.execute();
    }
}