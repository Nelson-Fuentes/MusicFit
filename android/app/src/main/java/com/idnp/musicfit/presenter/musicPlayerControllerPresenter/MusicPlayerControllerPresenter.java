package com.idnp.musicfit.presenter.musicPlayerControllerPresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.iMusicPlayerControllerView;

public class MusicPlayerControllerPresenter implements iMusicPlayerControllerPresenter {

    public static final int STOPPED = -1;
    public static final int PAUSED = 0;
    public static final int PLAYED = 1;

    public static iMusicPlayerControllerPresenter musicPlayerControllerPresenter;

    private iMusicPlayerControllerView musicPlayerControllerView;
    private Context context;

    public MusicPlayerControllerPresenter(iMusicPlayerControllerView musicPlayerControllerView,Context context) {
        this.context=context;
        this.musicPlayerControllerView = musicPlayerControllerView;

    }

    public Song getCurrentMusic(){
        return MusicPlayerService.musicPlayerService.getCurrentMusic();
    }

    @Override
    public void start(){
        if(context!=null) {
            MusicPlayerService.context = context;
            context.stopService(new Intent(context, MusicPlayerService.class));
            context.startService(new Intent(context, MusicPlayerService.class));
            this.musicPlayerControllerView.loadSelectedMusic();
            this.musicPlayerControllerView.play();
        }
    }

    @Override
    public void play() {

        if(!MusicPlayerService.mediaPlayer.isPlaying()){
            this.musicPlayerControllerView.play();
            MusicPlayerService.musicPlayerService.play();
        }
        else{
            pause();
        }

    }

    @Override
    public void pause() {
        MusicPlayerService.musicPlayerService.pause();
        this.musicPlayerControllerView.pause();
    }

    @Override
    public void stop() {

        context.stopService(new Intent(context,MusicPlayerService.class));
        this.musicPlayerControllerView.stop();
    }

    @Override
    public void back() {
        MusicPlayerService.musicPlayerService.back();
        this.musicPlayerControllerView.back();
    }

    @Override
    public void advance() {
        MusicPlayerService.musicPlayerService.advance();
        this.musicPlayerControllerView.advance();
    }

    @Override
    public void random() {

    }

    @Override
    public void repeat() {
        boolean state=MusicPlayerService.musicPlayerService.repeatState();
        this.musicPlayerControllerView.repeat(state);
        MusicPlayerService.musicPlayerService.changeRepeat();
    }

    @Override
    public void setView(iMusicPlayerControllerView view) {
        if (view != null){
            this.musicPlayerControllerView.stop();
            this.musicPlayerControllerView = view;
            switch (MusicPlayerService.musicPlayerService.getState()){
                case MusicPlayerService.PLAYED:
                    this.musicPlayerControllerView.play();
                    break;
                case MusicPlayerService.PAUSED:
                    this.musicPlayerControllerView.pause();
                    break;
                case MusicPlayerService.STOPPED:
                    this.musicPlayerControllerView.stop();
                    break;
            }
        }

    }

}