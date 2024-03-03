package com.example.playlistmigrator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.playlistmigrator.auth.AuthObject;
import com.example.playlistmigrator.playlists.Playlist;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchSourcePlayListTask extends BackgroundTask<List<Playlist>> {
    private final static String AUTH_URL = "https://accounts.spotify.com/api/";
    private final static String API = "https://api.spotify.com/v1/";
    private final static String CLIENT_ID = "b2474e4c07cf4d0f9ef7776f59ce5a0d";
    private final static String TOKEN = "9d73237eada04c8fbeff170874c58e15";
    private final Context context;

    public FetchSourcePlayListTask(Context context) {
        this.context = context;
    }
    @Override
    protected void postExecute(List<Playlist> data) {
        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "API data: " +
                data.get(0).getName());
    }

    @Override
    public List<Playlist> call() throws Exception {
        if(this.context instanceof MainActivity) {
            ((MainActivity)context).runOnUiThread(() -> {
                Toast.makeText(context, "Fetching data", Toast.LENGTH_SHORT).show();
            });
        }

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
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpotifyAPI api = rf.create(SpotifyAPI.class);
        Response<List<Playlist>> response2 = api.getUserPlaylists("edwinacl",
                "Bearer " + token).execute();

        return response2.body();
    }

    private String authenticateUser() throws IOException {
        // here goes the retrofit API call
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpotifyAPI api = rf.create(SpotifyAPI.class);
        Response<AuthObject> response = api
                .authenticate("client_credentials", CLIENT_ID, TOKEN).execute();

        return response.body().getAccessToken() == null ?
                "" : response.body().getAccessToken();
    }
}
