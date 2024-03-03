package com.example.playlistmigrator.playlists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ViewHolder> {
    private final List<Playlist> playLists;

    public PlaylistsAdapter(List<Playlist> playLists) {
        this.playLists = playLists;
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
        holder.getNameTextView().setText(playLists.get(position).getName());
        holder.getIdTextView().setText(playLists.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            id = itemView.findViewById(R.id.item_id);
        }

        public TextView getNameTextView() {
            return name;
        }

        public TextView getIdTextView() {
            return id;
        }
    }
}
