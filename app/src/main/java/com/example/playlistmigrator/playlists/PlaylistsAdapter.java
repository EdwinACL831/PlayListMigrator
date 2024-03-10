package com.example.playlistmigrator.playlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playlistmigrator.R;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ViewHolder> {
    private final List<Playlist> playLists;
    private Context context;

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
        holder.getNameTextView().setText("Title:\t" + playLists.get(position).getName());
        holder.getIdTextView().setText("id:\t" + playLists.get(position).getId());
        holder.setOnClickListener(v -> {
            if(context instanceof PlaylistsActivity) {
                ((PlaylistsActivity)context).runOnUiThread(
                        () -> Toast.makeText(context, "you clicked " +
                                playLists.get(position).getName() + " playlist", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;
        private View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            id = itemView.findViewById(R.id.item_id);
            this.itemView = itemView;
        }

        public TextView getNameTextView() {
            return name;
        }

        public TextView getIdTextView() {
            return id;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }
}
