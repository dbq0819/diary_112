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
    private Button buttonCreate;
    private RecyclerView recyclerView;
    private DiaryAdapter adapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCreate = findViewById(R.id.buttonCreate);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiaryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        database = AppDatabase.getInstance(this);

        // 跳转到编辑页面
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        // 设置点击监听器
        adapter.setOnItemClickListener(new DiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DiaryEntry diaryEntry) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("DIARY_ENTRY_ID", diaryEntry.getId()); // 传递日记项的ID
                startActivity(intent);
            }
        });

        // 设置删除监听器
        adapter.setOnItemDeleteListener(new DiaryAdapter.OnItemDeleteListener() {
            @Override
            public void onItemDelete(DiaryEntry diaryEntry) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        database.diaryEntryDao().delete(diaryEntry); // 删除日记项
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        loadEntries(); // 删除后刷新列表
                    }
                }.execute();
            }
        });

        loadEntries();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEntries(); // 每次返回首页时刷新日记列表
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
                adapter = new DiaryAdapter(diaryEntries); // 创建新的适配器实例
                recyclerView.setAdapter(adapter); // 设置适配器

                // 重新设置监听器
                adapter.setOnItemClickListener(new DiaryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DiaryEntry diaryEntry) {
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("DIARY_ENTRY_ID", diaryEntry.getId()); // 传递日记项的ID
                        startActivity(intent);
                    }
                });

                // 重新设置删除监听器
                adapter.setOnItemDeleteListener(new DiaryAdapter.OnItemDeleteListener() {
                    @Override
                    public void onItemDelete(DiaryEntry diaryEntry) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                database.diaryEntryDao().delete(diaryEntry); // 删除日记项
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                loadEntries(); // 删除后刷新列表
                            }
                        }.execute();
                    }
                });
            }
        }.execute();
    }
}