package com.example.playlistmigrator;

import com.google.gson.annotations.SerializedName;

public class AuthObject {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }
}
