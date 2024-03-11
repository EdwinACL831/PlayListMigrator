package com.example.playlistmigrator.tracksselection;

import com.google.gson.annotations.SerializedName;

public class Artist {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
