package com.example.playlistmigrator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.playlistmigrator.auth.AuthObject;
import com.example.playlistmigrator.playlists.PlaylistAPIResponse;
import com.example.playlistmigrator.playlists.PlaylistsActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchSourcePlayListTask extends BackgroundTask<PlaylistAPIResponse> {
    private final static String AUTH_URL = "https://accounts.spotify.com/api/";
    private final static String API = "https://api.spotify.com/v1/";
    private String cliendId;
    private String token;
    private final Context context;

    public FetchSourcePlayListTask(Context context) {
        this.context = context;
        InputStream inputStream = context.getResources().openRawResource(R.raw.config);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            cliendId = properties.getProperty("client_id");
            token = properties.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void postExecute(PlaylistAPIResponse data) {
        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "API data: " +
                data.getTotalPlayLists());
        runOnUIThread(() -> context.startActivity( new Intent(context, PlaylistsActivity.class)));
    }

    @Override
    public PlaylistAPIResponse call() throws Exception {
        runOnUIThread(() -> Toast.makeText(context, "Fetching data", Toast.LENGTH_SHORT).show());

        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "Start API authentication");
        // here goes the auth's spotify API call using retrofit
        String token = authenticateUser();
        if (token.isEmpty()) {
            String msg = "authentication failed. check app's API credentials";
            Log.e(FetchSourcePlayListTask.class.getSimpleName(), msg);
            throw new RuntimeException(msg);
        }
        Log.d(FetchSourcePlayListTask.class.getSimpleName(),
                "Finished API authentication successfully");

        // fetch the playlists
        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "Start fetch playlists API");
        PlaylistAPIResponse response = fetchPlaylistsByUser("edwinacl", token);
        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "Finish fetch playlists API");
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
                .authenticate("client_credentials", cliendId, token).execute();

        return response.body() == null ?
                "" : response.body().getAccessToken();
    }

    private PlaylistAPIResponse fetchPlaylistsByUser(String username, String token) throws IOException {
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(API)
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
