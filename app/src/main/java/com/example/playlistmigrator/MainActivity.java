package com.example.playlistmigrator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class MainActivity extends ComponentActivity {
    private Button button;
    private EditText username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        button = findViewById(R.id.button);
        username = findViewById(R.id.edit_text_username);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchSourcePlayListTask(MainActivity.this, username.getEditableText().toString())
                        .executeTask();
            }
        });
    }
}
