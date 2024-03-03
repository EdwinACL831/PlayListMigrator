package com.example.playlistmigrator.playlists;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayListsAdapter extends RecyclerView.Adapter {
    private final List<Playlist> playLists;

    public PlayListsAdapter(List<Playlist> playLists) {
        this.playLists = playLists;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }
}
