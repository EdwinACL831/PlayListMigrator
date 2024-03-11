package com.example.playlistmigrator.playlists;

import com.google.gson.annotations.SerializedName;

public class TracksInfo {
    @SerializedName("href")
    private String href;

    @SerializedName("total")
    private int totalTracks;

    public int getTotalTracks() {
        return totalTracks;
    }
}
