package com.example.playlistmigrator.playlists;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;
import com.example.playlistmigrator.tracksselection.TrackSelectionActivity;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ViewHolder> {
    private static final String TAG = PlaylistsAdapter.class.getSimpleName();
    private final List<Playlist> playLists;
    private final Context context;

    public PlaylistsAdapter(List<Playlist> playLists, Context context) {
        this.playLists = playLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View playListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.play_list_item, parent, false);

        return new ViewHolder(playListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = playLists.get(position).getName();
        String playlistId = playLists.get(position).getId();
        int numberOfTracks = playLists.get(position).getTracksInfo().getTotalTracks();

        /*
        * This chunk of code is in charge of sending the playlist data selected to the next activity
        * The id and the name of it is sent
        */
        holder.getNameTextView().setText(String.format("Title:\t%s", name));
        holder.getIdTextView().setText(String.format("ID:\t%s", playlistId));
        holder.setOnClickListener(v -> runOnUIThread(() -> {
            Log.d(TAG, String.format("Loading [%s] playlist's tracks", name));
            Intent intent = new Intent(context, TrackSelectionActivity.class);
            intent.putExtra(PlaylistsActivity.PLAYLIST_ID_TO_LOAD_KEY, playlistId);
            intent.putExtra(PlaylistsActivity.PLAYLIST_NAME_TO_LOAD_KEY, name);
            context.startActivity(intent);
            Log.d(TAG, String.format("[%s] playlist's tracks loaded", name));
        }));
        holder.getInfoBtn().setOnClickListener(v -> {
            Log.d(TAG, String.format("info button %d clicked", position));
            setupAlertDialogAndDisplay(name, numberOfTracks);
        });
    }

    private void setupAlertDialogAndDisplay(String playlistName, int numberOfTracks) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(playlistName)
                .setMessage("Number of tracks: " + numberOfTracks)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Handle OK button click
                    Log.d(TAG, "Closing Alert Dialog");
                    dialog.dismiss();
                });
        AlertDialog dialog = builder.create();
        dialog.create();
        runOnUIThread(dialog::show);
    }

    private void runOnUIThread(Runnable runnable) {
        if(context instanceof PlaylistsActivity) {
            ((PlaylistsActivity)context).runOnUiThread(runnable);
        }
    }
    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;
        private ImageButton infoBtn;
        private View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            id = itemView.findViewById(R.id.item_id);
            infoBtn = itemView.findViewById(R.id.info_btn);
            this.itemView = itemView;
        }

        public TextView getNameTextView() {
            return name;
        }

        public TextView getIdTextView() {
            return id;
        }

        public ImageButton getInfoBtn() {
            return infoBtn;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }
}
