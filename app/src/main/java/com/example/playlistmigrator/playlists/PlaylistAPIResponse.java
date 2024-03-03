package com.example.playlistmigrator.playlists;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaylistAPIResponse {
    @SerializedName("items")
    private List<Playlist> playlists;
    @SerializedName("total")
    private int totalPlaylists;

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public int getTotalPlayLists() {
        return totalPlaylists;
    }
}
