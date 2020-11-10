package com.idnp.musicfit.models.entities;

import android.util.Log;

import com.idnp.musicfit.R;

import java.util.ArrayList;

public class MusicPlayList {
    private ArrayList<Song> musicList;

    public MusicPlayList(){
        musicList = new ArrayList<Song>();
        loadMusicPlayList();
    }
    public void loadMusicPlayList(){
        this.musicList.add(new Song(R.raw.song1,"Musica 1","Artista 1"));
        this.musicList.add(new Song(R.raw.song2,"Musica 2","Artista 2"));
        this.musicList.add(new Song(R.raw.song3,"Musica 3","Artista 3"));
        this.musicList.add(new Song(R.raw.song4,"Musica 4","Artista 4"));

    }

    public ArrayList<Song> getMusicPlayList(){
        return this.musicList;
    }

}
