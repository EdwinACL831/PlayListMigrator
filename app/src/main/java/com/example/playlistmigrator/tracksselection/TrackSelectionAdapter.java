package com.example.playlistmigrator.tracksselection;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;

import java.util.ArrayList;
import java.util.List;

public class TrackSelectionAdapter extends RecyclerView.Adapter<TrackSelectionAdapter.ViewHolder> {
    private static final String TAG = TrackSelectionAdapter.class.getSimpleName();
    private final Context context;
    private List<Track> tracks;

    private List<Track> tracksSelected;
    public TrackSelectionAdapter(Context context, List<Track> tracks) {
        this.tracks = tracks;
        this.context = context;
        tracksSelected = new ArrayList<>(tracks);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trackList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_selection_item, parent, false);
        return new ViewHolder(trackList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrackInfo trackInfo = tracks.get(position).getTrackInfo();
        holder.setTrackName(trackInfo.getName());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < trackInfo.getArtists().size(); i++) {
            sb.append(trackInfo.getArtists().get(i).getName());
            if( i < trackInfo.getArtists().size() - 1) {
                sb.append(" & ");
            }
        }
        holder.setTrackArtist(sb.toString());
        holder.setInfoBtnOnClickListener(v ->
                setupAlertDialogAndDisplay(trackInfo.getName(), trackInfo.getDurationInSeconds()));
        holder.setOnCheckboxChange((buttonView, isChecked) ->
                updateTrackListToMigrate(isChecked, tracksSelected, tracks.get(position)));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    private void updateTrackListToMigrate(boolean isChecked, List<Track> selected, Track currentTrack) {
        if(isChecked && !selected.contains(currentTrack)) {
            selected.add(currentTrack);
            Log.d(TAG, String.format("track %s added to list to be exported",
                    currentTrack.getTrackInfo().getName()));
        } else {
            selected.remove(currentTrack);
            Log.d(TAG, String.format("track %s removed from list to be exported",
                    currentTrack.getTrackInfo().getName()));
        }
    }

    private void setupAlertDialogAndDisplay(String trackName, double durationInSec) {
        int minutes = (int)durationInSec / 60;
        int secs = (int)(durationInSec - (minutes * 60));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(trackName)
                .setMessage(String.format("duration is %02d:%02d", minutes, secs))
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
        if(context instanceof TrackSelectionActivity) {
            ((TrackSelectionActivity)context).runOnUiThread(runnable);
        }
    }

    public List<Track> getSelectedTracks() {
        return tracksSelected;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trackName;
        private TextView trackArtist;
        private ImageView trackInfoBtn;
        private CheckBox trackCheckbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trackName = itemView.findViewById(R.id.track_name);
            trackName.setSingleLine(true);
            trackName.setEllipsize(TextUtils.TruncateAt.END);

            trackArtist = itemView.findViewById(R.id.track_artist);
            trackArtist.setSingleLine(true);
            trackArtist.setEllipsize(TextUtils.TruncateAt.END);

            trackInfoBtn = itemView.findViewById(R.id.track_info_btn);
            trackCheckbox = itemView.findViewById(R.id.track_checkbox);
        }

        public void setTrackName(String name) {
            trackName.setText(name);
        }

        public void setTrackArtist(String artist) {
            trackArtist.setText(artist);
        }

        public void setInfoBtnOnClickListener(View.OnClickListener onClickListener) {
            trackInfoBtn.setOnClickListener(onClickListener);
        }

        public void setOnCheckboxChange(CompoundButton.OnCheckedChangeListener onCheckedChangeListener ) {
            trackCheckbox.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }
}
