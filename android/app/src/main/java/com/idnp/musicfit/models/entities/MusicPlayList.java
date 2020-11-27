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

public class MusicPlayList {

    public static MusicPlayList musicPlayList;
    private ArrayList<Song> musicList;
    /*private LayoutInflater mininflater;
    private Context context;*/

    public MusicPlayList(){
        this.musicList=new ArrayList<Song>();
        loadMusicPlayList();
    }
    public void loadMusicPlayList(){
        this.musicList=new ArrayList<Song>();
        this.musicList.add(new Song(R.raw.song1,"How you like that","Blackpink"));
        this.musicList.add(new Song(R.raw.song2,"Blue Bird","Ikimonogakari"));
        this.musicList.add(new Song(R.raw.song3,"I can't stop me","Twice"));
        this.musicList.add(new Song(R.raw.song4,"Pegasus Fantasy","Meuren Mendo"));
        this.musicList.add(new Song(R.raw.song5,"Hoy","Gian Marco"));
        this.musicList.add(new Song(R.raw.song6,"Dance Monkey","Tones and I"));
        this.musicList.add(new Song(R.raw.song7,"Empire","Shakira"));
        this.musicList.add(new Song(R.raw.song8,"Nothing On You","Ed sheeran"));
        this.musicList.add(new Song(R.raw.song9,"Black and Yellow","Wiz Khalifa"));
        /*Este es un comentario de prueba*/
    }

    public ArrayList<Song> getMusicPlayList(){
        this.musicList=new ArrayList<Song>();
        this.musicList.add(new Song(R.raw.song1,"How you like that","Blackpink"));
        this.musicList.add(new Song(R.raw.song2,"Blue Bird","Ikimonogakari"));
        this.musicList.add(new Song(R.raw.song3,"I can't stop me","Twice"));
        this.musicList.add(new Song(R.raw.song4,"Pegasus Fantasy","Meuren Mendo"));
        this.musicList.add(new Song(R.raw.song5,"Hoy","Gian Marco"));
        this.musicList.add(new Song(R.raw.song6,"Dance Monkey","Tones and I"));
        this.musicList.add(new Song(R.raw.song7,"Empire","Shakira"));
        this.musicList.add(new Song(R.raw.song8,"Nothing On You","Ed sheeran"));
        this.musicList.add(new Song(R.raw.song9,"Black and Yellow","Wiz Khalifa"));
        return this.musicList;
    }

    /*public int getItemCount(){
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

    }*/
}
