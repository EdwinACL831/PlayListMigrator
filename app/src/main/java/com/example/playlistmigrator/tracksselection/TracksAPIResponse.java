package com.example.playlistmigrator.tracksselection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TracksAPIResponse {
    @SerializedName("items")
    private List<Track> tracks;

    @SerializedName("total")
    private int total;

    public TracksAPIResponse(List<Track> tracks, int total) {
        this.tracks = tracks;
        this.total = total;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public int getTotal() {
        return total;
    }
}
