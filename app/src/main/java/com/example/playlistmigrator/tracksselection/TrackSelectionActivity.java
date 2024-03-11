package com.example.playlistmigrator.tracksselection;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;
import com.example.playlistmigrator.playlists.PlaylistsActivity;

public class TrackSelectionActivity extends ComponentActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_selection_activity);
        recyclerView = findViewById(R.id.track_selection_list);

        Intent intent = getIntent();
        String playlistId = intent.getStringExtra(PlaylistsActivity.PLAYLIST_ID_TO_LOAD_KEY);
        new FetchPlaylistTracksTask(TrackSelectionActivity.this, playlistId, recyclerView).executeTask();
    }
}
