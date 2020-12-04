package com.idnp.musicfit.presenter.musicPlayerListPresenter;

import com.idnp.musicfit.models.entities.MusicPlayList;

import com.idnp.musicfit.views.fragments.musicPlayerListView.iMusicPlayerListView;
import com.idnp.musicfit.views.fragments.trainingReportListView.iTrainingReportListView;

public class MusicPlayerListPresenter implements iMusicPlayerListPresenter{

    private iMusicPlayerListView musicPlayerListView;

    public MusicPlayerListPresenter(iMusicPlayerListView musicPlayerListView){
        this.musicPlayerListView = musicPlayerListView;
    }

    @Override
    public void loadMusicList() {
        this.musicPlayerListView.showMusicList(new MusicPlayList().getMusicPlayList());
    }
}
