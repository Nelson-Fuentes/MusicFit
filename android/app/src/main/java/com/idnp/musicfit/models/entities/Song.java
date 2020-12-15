package com.idnp.musicfit.models.entities;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import androidx.annotation.RawRes;

import java.io.File;

public class Song {

    private String music;
    private String name;
    private String artist;
    private String album;
    private String duration;

    public Song(String music, String name, String artist, String album, String duration){
        this.music=music;
        this.name=name;
        this.artist=artist;
        this.album=album;
        this.duration=duration;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMusic() {
        return music;
    }

    public String getName() {
        return name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
