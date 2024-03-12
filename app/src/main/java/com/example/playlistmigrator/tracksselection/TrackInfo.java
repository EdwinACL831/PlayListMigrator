package com.example.playlistmigrator.tracksselection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackInfo {
    @SerializedName("name")
    private String name;

    @SerializedName("duration_ms")
    private int durationInMillis;

    @SerializedName("artists")
    private List<Artist> artists;

    public String getName() {
        return name;
    }

    public double getDurationInSeconds() {
        return (double)durationInMillis / 1000;
    }

    public List<Artist> getArtists() {
        return artists;
    }
}
