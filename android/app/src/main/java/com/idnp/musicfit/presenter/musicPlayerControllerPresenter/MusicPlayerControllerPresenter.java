package com.idnp.musicfit.presenter.musicPlayerControllerPresenter;

import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.iMusicPlayerControllerView;

import java.util.ArrayList;

public class MusicPlayerControllerPresenter implements iMusicPlayerControllerPresenter {

    public static iMusicPlayerControllerPresenter musicPlayerControllerPresenter;

    private iMusicPlayerControllerView musicPlayerControllerView;

    public MusicPlayerControllerPresenter(iMusicPlayerControllerView musicPlayerControllerView) {
        this.musicPlayerControllerView = musicPlayerControllerView;
    }

    @Override
    public void play() {
        MusicPlayerService.musicPlayerService.play();
        this.musicPlayerControllerView.play();
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