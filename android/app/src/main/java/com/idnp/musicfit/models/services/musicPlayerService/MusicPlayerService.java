package com.idnp.musicfit.models.services.musicPlayerService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.idnp.musicfit.models.entities.MusicPlayList;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.MusicPlayerControllerFragment;

import java.util.ArrayList;

public class MusicPlayerService extends AppCompatActivity {

    public static MusicPlayerService musicPlayerService;
    public static final int STOPPED = -1;
    public static final int PAUSED = 0;
    public static final int PLAYED = 1;
    private static int state=STOPPED;
    private static ArrayList<Song> musicList;

    private static int position=0;

    public MusicPlayerService(){

        state = STOPPED;
    }

    public void loadMusicPlayList(){
        MusicPlayList list=new MusicPlayList();
        musicList=list.getMusicPlayList();

    }

    public Song getCurrentMusic(){
        loadMusicPlayList();
        return musicList.get(position);
    }

    public Song play(){
        loadMusicPlayList();
        state = PLAYED;
        return musicList.get(position);
    }
    public void pause() {

        state = PAUSED;
    }
    public void stop() {

        state = STOPPED;
    }
    public void back(){
        if(position==0){
            position=musicList.size()-1;
        }
        else{
            position--;
        }
    }
    public void advance(){
        if(position==musicList.size()-1){
            position=0;
        }
        else{
            position++;
        }
    }

    public int getState() {

        return state;
    }
}
