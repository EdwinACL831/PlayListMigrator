package com.example.playlistmigrator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.playlistmigrator.auth.AuthObject;
import com.example.playlistmigrator.common.ConfigPropertiesManager;
import com.example.playlistmigrator.playlists.PlaylistAPIResponse;
import com.example.playlistmigrator.playlists.PlaylistsActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchSourcePlayListTask extends BackgroundTask<PlaylistAPIResponse> {
    public final static String API_RESPONSE_KEY = "API_RESPONSE";
    private final static String AUTH_URL = "https://accounts.spotify.com/api/";
    private final static String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/";
    private static final String TAG = FetchSourcePlayListTask.class.getSimpleName();
    private final String username;
    private String clientId;
    private String token;
    private final Context context;

    public FetchSourcePlayListTask(Context context, String username) {
        this.username = username;
        this.context = context;
        clientId = ConfigPropertiesManager.getInstance(context).getSpotifyClientId();
        token =  ConfigPropertiesManager.getInstance(context).getSpotifyClientToken();
    }
    @Override
    protected void postExecute(PlaylistAPIResponse data) {
        String playlistAPIResponseJSON = new Gson().toJson(data);
        Log.d(TAG, "API data: " +
                playlistAPIResponseJSON);
        Intent intent = new Intent(context, PlaylistsActivity.class);
        intent.putExtra(API_RESPONSE_KEY, playlistAPIResponseJSON);
        runOnUIThread(() -> context.startActivity(intent));
    }

    @Override
    public PlaylistAPIResponse call() throws Exception {
        runOnUIThread(() -> Toast.makeText(context, "Fetching data", Toast.LENGTH_SHORT).show());

        Log.d(TAG, "Start API authentication");
        // here goes the auth's spotify API call using retrofit
        String token = authenticateUser();
        if (token.isEmpty()) {
            String msg = "authentication failed. check app's API credentials";
            Log.e(TAG, msg);
            throw new RuntimeException(msg);
        }
        Log.d(TAG,
                "Finished API authentication successfully");

        // fetch the playlists
        Log.d(TAG, "Start fetch playlists API");
        PlaylistAPIResponse response = fetchPlaylistsByUser(this.username, token);
        Log.d(TAG, "Finish fetch playlists API");
        return response;
    }

    private String authenticateUser() throws IOException {
        // here goes the retrofit API call
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpotifyAPI api = rf.create(SpotifyAPI.class);
        Response<AuthObject> response = api
                .authenticate("client_credentials", clientId, token).execute();

        return response.body() == null ?
                "" : response.body().getAccessToken();
    }

    private PlaylistAPIResponse fetchPlaylistsByUser(String username, String token) throws IOException {
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(SPOTIFY_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpotifyAPI api = rf.create(SpotifyAPI.class);
        Response<PlaylistAPIResponse> response = api.getUserPlaylists(username,
                "Bearer " + token).execute();
        return response.body();
    }

    private void runOnUIThread(Runnable runnable) {
        if(context instanceof MainActivity) {
            ((MainActivity)context).runOnUiThread(runnable);
        }
    }
}
