package com.example.playlistmigrator.playlists;

import com.google.gson.annotations.SerializedName;

public class Playlist {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    public Playlist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
