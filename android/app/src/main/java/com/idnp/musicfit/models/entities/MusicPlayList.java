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
        this.musicList.add(new Song(R.raw.song1,"How you like that","Blackpink"));
        this.musicList.add(new Song(R.raw.song2,"Blue Bird","Ikimonogakari"));
        this.musicList.add(new Song(R.raw.song3,"I can't stop me","Twice"));
        this.musicList.add(new Song(R.raw.song4,"Pegasus Fantasy","Meuren Mendo"));

    }

    public ArrayList<Song> getMusicPlayList(){
        return this.musicList;
    }

}
