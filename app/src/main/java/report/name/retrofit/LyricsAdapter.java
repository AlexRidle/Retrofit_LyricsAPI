package report.name.retrofit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.ViewHolder> {
    private List<LyricsModel> lyricsModelList;

    LyricsAdapter(List<LyricsModel> lyricsModelList) {
        this.lyricsModelList = lyricsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyrics_item, parent, false);
        return new ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LyricsModel lyric = lyricsModelList.get(position);
        holder.lyrics.setText(lyric.getMessage().getBody().getLyrics().getLyricsBody());
        holder.song.setText(lyric.getMessage().getBody().getLyrics().getLyricsLanguage());
    }

    @Override
    public int getItemCount() {
        if (lyricsModelList == null) return 0;
        return lyricsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView lyrics;
        TextView song;

        ViewHolder(View itemView) {
            super(itemView);
            lyrics = itemView.findViewById(R.id.lyricsitem_lyrics);
            song = itemView.findViewById(R.id.lyricsitem_song);
        }
    }
}

