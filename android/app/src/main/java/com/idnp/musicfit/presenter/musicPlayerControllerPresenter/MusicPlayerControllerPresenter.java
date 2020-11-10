package com.idnp.musicfit.presenter.musicPlayerControllerPresenter;

import android.app.Activity;
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


    public MusicPlayerControllerPresenter(iMusicPlayerControllerView musicPlayerControllerView) {

        this.musicPlayerControllerView = musicPlayerControllerView;

    }

    public Song getCurrentMusic(){
        return MusicPlayerService.musicPlayerService.getCurrentMusic();
    }

    @Override
    public void play() {

        if(MusicPlayerService.musicPlayerService.getState() != PLAYED){
            this.musicPlayerControllerView.play();
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
        MusicPlayerService.musicPlayerService.stop();
        this.musicPlayerControllerView.stop();
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