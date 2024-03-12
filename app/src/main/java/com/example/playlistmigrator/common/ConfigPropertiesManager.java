package com.example.playlistmigrator.common;

import android.content.Context;

import com.example.playlistmigrator.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesManager {
    private static final String SPOTIFY_CLIENT_ID_PROPERTY_KEY = "client_id";
    private static final String SPOTIFY_CLIENT_TOKEN_PROPERTY_KEY = "token";
    private static final String GOOGLE_CLIENT_ID_PROPERTY_KEY = "google_client_id";
    private static ConfigPropertiesManager INSTANCE = null;
    private Properties properties;
    public static ConfigPropertiesManager getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new ConfigPropertiesManager(context);
        }

        return INSTANCE;
    }
    private ConfigPropertiesManager(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.config);
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSpotifyClientId() {
        return properties.getProperty(SPOTIFY_CLIENT_ID_PROPERTY_KEY);
    }

    public String getSpotifyClientToken() {
        return properties.getProperty(SPOTIFY_CLIENT_TOKEN_PROPERTY_KEY);
    }

    public String getGoogleClientId() {
        return properties.getProperty(GOOGLE_CLIENT_ID_PROPERTY_KEY);
    }
}
