package com.example.playlistmigrator.playlists;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.FetchSourcePlayListTask;
import com.example.playlistmigrator.R;
import com.google.gson.Gson;

/**
 * Displays the different playlists that were found for the specified username
 */
public class PlaylistsActivity extends ComponentActivity {

    public static final String PLAYLIST_ID_TO_LOAD_KEY = "PLAYLIST_TO_LOAD";
    public static final String PLAYLIST_NAME_TO_LOAD_KEY = "PLAYLIST_NAME_TO_LOAD_KEY";
    private RecyclerView recyclerView;
    private PlaylistsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_lists_activity);
        recyclerView = findViewById(R.id.recycle_view_play_lists);

        // fetch playlist from intent
        String playlistAPIResponseJSON = getIntent().getStringExtra(FetchSourcePlayListTask.API_RESPONSE_KEY);
        if (null != playlistAPIResponseJSON) {
            PlaylistAPIResponse apiResponse = new Gson().fromJson(playlistAPIResponseJSON, PlaylistAPIResponse.class);
            adapter = new PlaylistsAdapter(apiResponse.getPlaylists(), PlaylistsActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(PlaylistsActivity.this));
            recyclerView.setAdapter(adapter);
        }
    }
}
