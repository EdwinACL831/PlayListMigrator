package com.example.playlistmigrator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchSourcePlayListTask extends BackgroundTask<AuthObject> {
    private final static String URL = "https://accounts.spotify.com/api/";
    private final static String CLIENT_ID = "b2474e4c07cf4d0f9ef7776f59ce5a0d";
    private final static String SECRET = "9d73237eada04c8fbeff170874c58e15";
    private final Context context;

    public FetchSourcePlayListTask(Context context) {
        this.context = context;
    }
    @Override
    protected void postExecute(AuthObject data) {
        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "API data: " + data.getAccessToken());
    }

    @Override
    public AuthObject call() throws Exception {
        if(this.context instanceof MainActivity) {
            ((MainActivity)context).runOnUiThread(() -> {
                Toast.makeText(context, "Fetching data", Toast.LENGTH_SHORT).show();
            });
        }

        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "Start getting API data");
        // here goes the retrofit API call
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpotifyAPI api = rf.create(SpotifyAPI.class);
        Response<AuthObject> response = api
                .authenticate("client_credentials", CLIENT_ID, SECRET).execute();
        Log.d(FetchSourcePlayListTask.class.getSimpleName(), "Finished getting API data");
        return response.body();
    }
}
