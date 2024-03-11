package com.example.playlistmigrator.tracksselection;

import com.google.gson.annotations.SerializedName;

public class Track {
    @SerializedName("track")
    private TrackInfo trackInfo;

    public TrackInfo getTrackInfo() {
        return trackInfo;
    }
}
