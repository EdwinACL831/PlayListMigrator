package com.example.playlistmigrator;

import com.example.playlistmigrator.auth.AuthObject;
import com.example.playlistmigrator.playlists.PlaylistAPIResponse;
import com.example.playlistmigrator.tracksselection.TracksAPIResponse;

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
    Call<PlaylistAPIResponse> getUserPlaylists(@Path("username") String username,
                                               @Header("Authorization") String authToken);

    @GET("playlists/{playlist_id}/tracks")
    Call<TracksAPIResponse> getPlaylistTracks(@Path("playlist_id") String playlistId,
                                              @Header("Authorization") String authToken);
}
