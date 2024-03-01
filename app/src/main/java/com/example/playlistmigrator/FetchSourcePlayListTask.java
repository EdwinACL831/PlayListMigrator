package com.example.playlistmigrator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class FetchSourcePlayListTask extends BackgroundTask {
    private final Context context;

    public FetchSourcePlayListTask(Context context) {
        this.context = context;
    }
    @Override
    protected void postExecute(Object o) {

    }

    @Override
    public Object call() throws Exception {
        if(this.context instanceof MainActivity) {
            ((MainActivity)context).runOnUiThread(() -> {
                Log.d(FetchSourcePlayListTask.class.getSimpleName(), "Running in background");
                Toast.makeText(context, "Running in background", Toast.LENGTH_SHORT).show();
            });

        }
        return null;
    }
}
