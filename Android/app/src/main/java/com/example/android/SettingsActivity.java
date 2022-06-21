package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ListView listView = findViewById(R.id.settings_list_view);
        this.list = new ArrayList<>();
        list.add("Change theme");
        list.add("Change server");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                                                                        this.list);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener((adapterView, view, i, l)-> {
            Intent intent;
            if (i == 0) {
                intent = new Intent(this, ThemeActivity.class);
            } else {
                intent = new Intent(this, ServerActivity.class);
            }
            startActivity(intent);
        });

        ImageButton backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(view-> finish());
    }
}