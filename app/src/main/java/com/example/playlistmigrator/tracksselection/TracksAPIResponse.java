package com.example.playlistmigrator.tracksselection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TracksAPIResponse {
    @SerializedName("items")
    private List<Track> tracks;

    @SerializedName("total")
    private int total;

    public List<Track> getTracks() {
        return tracks;
    }

    public int getTotal() {
        return total;
    }
}
