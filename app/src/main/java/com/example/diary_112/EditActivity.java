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

public class EditActivity extends AppCompatActivity{
    private EditText editTextTitle, editTextContent;
    private Button buttonSave;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSave = findViewById(R.id.buttonSave);

        database = AppDatabase.getInstance(this);

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

        // 生成 monthYear 字段（例如 "2023-10"）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String monthYear = sdf.format(new Date(timestamp));

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
                finish(); // 保存后关闭编辑页面
            }
        }.execute();
    }
}
