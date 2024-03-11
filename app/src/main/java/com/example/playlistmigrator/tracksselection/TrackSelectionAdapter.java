package com.example.playlistmigrator.tracksselection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;

import java.util.List;

public class TrackSelectionAdapter extends RecyclerView.Adapter<TrackSelectionAdapter.ViewHolder> {
    private List<Track> tracks;
    public TrackSelectionAdapter(List<Track> tracks) {
        this.tracks = tracks;
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
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trackName;
        private TextView trackArtist;
        private ImageView trackInfoBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trackName = itemView.findViewById(R.id.track_name);
            trackArtist = itemView.findViewById(R.id.track_artist);
            trackInfoBtn = itemView.findViewById(R.id.track_info_btn);
        }

        public void setTrackName(String name) {
            trackName.setText(name);
        }

        public void setTrackArtist(String artist) {
            trackArtist.setText(artist);
        }
    }
}
