package com.example.playlistmigrator.playlists;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsActivity extends ComponentActivity {
    private RecyclerView recyclerView;
    private PlaylistsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_lists_activity);
        recyclerView = findViewById(R.id.recycle_view_play_lists);

        List<Playlist> playLists = new ArrayList<>();
        playLists.add(new Playlist("123", "Playlist1"));
        playLists.add(new Playlist("987", "Playlist 2"));
        adapter = new PlaylistsAdapter(playLists);

        recyclerView.setLayoutManager(new LinearLayoutManager(PlaylistsActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
