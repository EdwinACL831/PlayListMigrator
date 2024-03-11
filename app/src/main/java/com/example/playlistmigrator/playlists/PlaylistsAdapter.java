package com.example.playlistmigrator.playlists;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.MainActivity;
import com.example.playlistmigrator.R;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ViewHolder> {
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

        holder.getNameTextView().setText(String.format("Title:\t%s", name));
        holder.getIdTextView().setText(String.format("ID:\t%s", playlistId));
        holder.setOnClickListener(v -> runOnUIThread(() -> Toast.makeText(context, "you clicked " +
                    playLists.get(position).getName() + " playlist", Toast.LENGTH_SHORT).show()));
        holder.getInfoBtn().setOnClickListener(v -> setupAlertDialog(name, numberOfTracks));
    }

    private void setupAlertDialog(String playlistName, int numberOfTracks) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(playlistName)
                .setMessage("Number of tracks: " + numberOfTracks)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Handle OK button click
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
