package com.example.playlistmigrator;

import com.example.playlistmigrator.auth.AuthObject;
import com.example.playlistmigrator.playlists.Playlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SpotifyAPI {
    @FormUrlEncoded
    @POST("token")
    Call<AuthObject> authenticate(@Field("grant_type") String grantType,
                                  @Field("client_id") String clientId,
                                  @Field("client_secret") String clientSecret);

    @GET("users/{username}/playlists")
    Call<List<Playlist>> getUserPlaylists(@Path("username") String username,
                                          @Header("Authorization") String authToken);
}
