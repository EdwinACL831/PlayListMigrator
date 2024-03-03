package com.example.playlistmigrator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class MainActivity extends ComponentActivity {
    private Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchSourcePlayListTask(MainActivity.this).executeTask();
            }
        });
    }
}
