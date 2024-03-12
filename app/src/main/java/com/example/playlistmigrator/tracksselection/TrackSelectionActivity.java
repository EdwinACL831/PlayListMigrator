package com.example.playlistmigrator.tracksselection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;
import com.example.playlistmigrator.auth.SelectDestinationActivity;
import com.example.playlistmigrator.playlists.PlaylistsActivity;
import com.google.gson.Gson;

import java.util.List;

public class TrackSelectionActivity extends ComponentActivity implements View.OnClickListener {
    public static final String SELECTED_TRACKS_KEY = "SELECTED_TRACKS_KEY";
    private final Context context = TrackSelectionActivity.this;
    private RecyclerView recyclerView;
    private Button continueBtn;
    private FetchPlaylistTracksTask playlistTracksTask;
    private String playlistName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_selection_activity);
        recyclerView = findViewById(R.id.track_selection_list);
        continueBtn = findViewById(R.id.track_selection_list_continue_btn);

        continueBtn.setOnClickListener(this);
        continueBtn.setEnabled(false);

        Intent intent = getIntent();
        String playlistId = intent.getStringExtra(PlaylistsActivity.PLAYLIST_ID_TO_LOAD_KEY);
        playlistName = intent.getStringExtra(PlaylistsActivity.PLAYLIST_NAME_TO_LOAD_KEY);

        /*
        * This portion of code is the one in charge of fetching the songs that make up the playlist
        * inside of this Class there is also logic to handle different view elements (not too good)
        */
        playlistTracksTask = new FetchPlaylistTracksTask(context,
                        playlistId,
                        recyclerView,
                        continueBtn);
        playlistTracksTask.executeTask();
    }

    /**
     * Sends the tracks that were selected to be migrated to the next Activity SelectedDestinationActivity
     * Also the name of the playlist is passed to that activity to be used as the default option for naming
     * the playlist in the other platform
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.track_selection_list_continue_btn) {
            List<Track> tracksSelected = playlistTracksTask.getSelectedTracks();
            Intent intent = new Intent(context, SelectDestinationActivity.class);
            intent.putExtra(SELECTED_TRACKS_KEY,
                    new Gson().toJson(new TracksAPIResponse(tracksSelected, tracksSelected.size())));
            intent.putExtra(PlaylistsActivity.PLAYLIST_NAME_TO_LOAD_KEY, playlistName);
            startActivity(intent);
        }
    }
}
