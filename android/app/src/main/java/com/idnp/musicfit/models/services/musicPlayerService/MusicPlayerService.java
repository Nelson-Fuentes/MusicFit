package com.idnp.musicfit.models.services.musicPlayerService;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.idnp.musicfit.models.entities.MusicPlayList;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.MusicPlayerControllerFragment;

import java.util.ArrayList;

public class MusicPlayerService extends Service {


    public static MusicPlayerService musicPlayerService=new MusicPlayerService();
    public static final int STOPPED = -1;
    public static final int PAUSED = 0;
    public static final int PLAYED = 1;
    public static final boolean REPEAT_ENABLED = true;
    public static final boolean REPEAT_DISABLED = false;
    public static final boolean RANDOM_ENABLED = true;
    public static final boolean RANDOM_DISABLED = false;
    private static int state=STOPPED;
    private static boolean repeat_state=REPEAT_DISABLED;
    private static boolean random_state=RANDOM_DISABLED;
    private static ArrayList<Song> musicList=new ArrayList<Song>();
    public static Context context;
    public static int position=0;

    public static MediaPlayer mediaPlayer=new MediaPlayer();

    /*PARA LAS NOTIFICACIONES*/
    public IBinder musicPlayerBinder = new MusicPlayerIBinder();
    /*FIN DE NOTIFICACIONES*/
    public static final String ACTION_PLAY="PLAY";
    public static final String ACTION_NEXT="NEXT";
    public static final String ACTION_PREV="PREV";
    
    public MusicPlayerService(){

    }
    public MusicPlayerService(Context context){
        MusicPlayerService.context =context;
        state = STOPPED;
        repeat_state=REPEAT_DISABLED;
    }


    public class MusicPlayerIBinder extends Binder{
        public MusicPlayerService getServicio(){
            return MusicPlayerService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionName = intent.getStringExtra("myActionName");
        Song music = getCurrentMusic();
        if (actionName != null) {
            if (actionName != null) {
                switch (actionName) {
                    case ACTION_PLAY:
                        play_pause();
                        MusicPlayerControllerFragment.musicPlayerControllerFragment.loadSelectedMusic();
                        break;
                    case ACTION_NEXT:
                        advance();
                        MusicPlayerControllerFragment.musicPlayerControllerFragment.advance();
                        break;
                    case ACTION_PREV:
                        back();
                        MusicPlayerControllerFragment.musicPlayerControllerFragment.back();
                        break;
                }
            }
        } else {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(music.getMusic()));
            mediaPlayer.start();
            return START_STICKY;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void loadMusicPlayList(){
        MusicPlayList list=new MusicPlayList();
        musicList=list.getMusicPlayList();

    }

    public Song getCurrentMusic(){
        loadMusicPlayList();
        return musicList.get(position);
    }

    public Song getSelectMusic(int position){
        MusicPlayList list=new MusicPlayList();
        musicList=list.getMusicPlayList();
        return musicList.get(position);
    }
    public void play_pause(){
        if(mediaPlayer.isPlaying()){
            MusicPlayerControllerFragment.musicPlayerControllerFragment.pause();
            pause();
        }
        else{
            MusicPlayerControllerFragment.musicPlayerControllerFragment.play();
            play();
        }
    }

    public Song play(){
        mediaPlayer.start();
        state = PLAYED;
        return musicList.get(position);
    }
    public void pause() {
        mediaPlayer.pause();
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
        mediaPlayer.stop();
        Song music = getCurrentMusic();
        mediaPlayer=MediaPlayer.create(context,Uri.parse(music.getMusic()));
        mediaPlayer.start();
    }
    public void advance(){
        if(position==musicList.size()-1){
            position=0;
        }
        else{
            position++;
        }
        mediaPlayer.stop();
        Song music = getCurrentMusic();
        mediaPlayer=MediaPlayer.create(context,Uri.parse(music.getMusic()));
        mediaPlayer.start();
    }
    public boolean repeatState(){
        return repeat_state;
    }
    public void changeRepeat(){
        if(repeat_state==REPEAT_ENABLED){
            repeat_state=REPEAT_DISABLED;
        }
        else{
            repeat_state=REPEAT_ENABLED;
        }
    }
    public int getState() {

        return state;
    }
    public void changeRandom(){
        if(random_state==RANDOM_ENABLED){
            random_state=RANDOM_DISABLED;
        }
        else{
            random_state=RANDOM_ENABLED;
        }
    }

    public boolean randomState(){
        return random_state;
    }

}
