package com.idnp.musicfit.models.entities;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import androidx.annotation.RawRes;

import java.io.File;

public class Song {
    //Este es un Comentario de Elmer Davila

    private class Ubicacion{
        private float latitud;
        private float longitud;

        public Ubicacion(float latitud,float longitud){
            this.latitud=latitud;
            this.longitud=longitud;
        }
        public float getLatitud(){
            return this.latitud;
        }
        public float getLongitud(){
            return this.longitud;
        }
    }

    private int music;
    private String name;
    private String artist;

    public Song(int music, String name, String artist){
        this.music=music;
        this.name=name;
        this.artist=artist;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMusic() {
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


}
