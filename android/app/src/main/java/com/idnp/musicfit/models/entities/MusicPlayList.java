package com.idnp.musicfit.models.entities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.idnp.musicfit.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayList extends RecyclerView.Adapter<MusicPlayList.ViewHolder> {
    private ArrayList<Song> musicList;
    private LayoutInflater mininflater;
    private Context context;

    public MusicPlayList(Context context){
        this.context=context;
        this.mininflater=LayoutInflater.from(context);
        this.musicList = new ArrayList<Song>();
        loadMusicPlayList();
    }
    public MusicPlayList(){
        this.musicList = new ArrayList<Song>();
        loadMusicPlayList();
    }
    public void loadMusicPlayList(){
        this.musicList.add(new Song(R.raw.song1,"How you like that","Blackpink"));
        this.musicList.add(new Song(R.raw.song2,"Blue Bird","Ikimonogakari"));
        this.musicList.add(new Song(R.raw.song3,"I can't stop me","Twice"));
        this.musicList.add(new Song(R.raw.song4,"Pegasus Fantasy","Meuren Mendo"));

    }

    public ArrayList<Song> getMusicPlayList(){
        return this.musicList;
    }

    public int getItemCount(){
        return this.musicList.size();
    }


    @Override
    public MusicPlayList.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view = mininflater.inflate(R.layout.list_item_player_mp3,null);
        return new MusicPlayList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MusicPlayList.ViewHolder holder , final int position) {
        holder.bindData(this.musicList.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name,artist;

        public ViewHolder(View itemView){
            super(itemView);
            iconImage= itemView.findViewById(R.id.icon_music);
            name= itemView.findViewById(R.id.name_music);
            artist= itemView.findViewById(R.id.artist_music);
        }

        void bindData(final Song music){
            name.setText(music.getName());
            artist.setText(music.getArtist());
        }

    }
}
