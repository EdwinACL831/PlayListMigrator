package com.example.playlistmigrator.tracksselection;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.BackgroundTask;
import com.example.playlistmigrator.R;
import com.example.playlistmigrator.SpotifyAPI;
import com.example.playlistmigrator.auth.AuthObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchPlaylistTracksTask extends BackgroundTask<TracksAPIResponse> {

    private final static String AUTH_URL = "https://accounts.spotify.com/api/";
    private final static String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/";
    private final static String TAG = FetchPlaylistTracksTask.class.getSimpleName();
    private final String playlistId;

    private String clientId;
    private String token;
    private Context context;
    private RecyclerView trackListView;
    public FetchPlaylistTracksTask(Context context, String playlistId, RecyclerView trackListView) {
        this.playlistId = playlistId;
        this.context = context;
        this.trackListView = trackListView;
        InputStream inputStream = context.getResources().openRawResource(R.raw.config);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            clientId = properties.getProperty("client_id");
            token = properties.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void postExecute(TracksAPIResponse tracksAPIResponse) {
        // here is where i have to add the logic to populate the adapter
        Log.d(TAG, String.format("Playlist tracks: %d", tracksAPIResponse.getTotal()));

        runOnUIThread(() -> {
            trackListView.setAdapter(new TrackSelectionAdapter(tracksAPIResponse.getTracks()));
            trackListView.setLayoutManager(new LinearLayoutManager(context));
        });
    }

    @Override
    public TracksAPIResponse call() throws Exception {
        // authenticate user
        Log.d(TAG, "Start API authentication");
        // here goes the auth's spotify API call using retrofit
        String token = authenticateUser();
        if (token.isEmpty()) {
            String msg = "authentication failed. check app's API credentials";
            Log.e(TAG, msg);
            throw new RuntimeException(msg);
        }
        Log.d(TAG, "Finished API authentication successfully");

        Log.d(TAG, "Start fetch playlists API");
        TracksAPIResponse response = fetchPlaylistTracksById(playlistId, token);
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

    private TracksAPIResponse fetchPlaylistTracksById(String playlistId, String token) throws IOException {
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(SPOTIFY_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpotifyAPI api = rf.create(SpotifyAPI.class);
        Response<TracksAPIResponse> response = api.getPlaylistTracks(playlistId, "Bearer " + token)
                .execute();
        return response.body();
    }

    private void runOnUIThread(Runnable runnable) {
        if(context instanceof TrackSelectionActivity) {
            ((TrackSelectionActivity)context).runOnUiThread(runnable);
        }
    }
}
