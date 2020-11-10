package com.idnp.musicfit.presenter.musicPlayerControllerPresenter;

import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.iMusicPlayerControllerView;

public interface iMusicPlayerControllerPresenter {
    public void play();
    public void pause();
    public void stop();
    public Song getCurrentMusic();
    public void setView(iMusicPlayerControllerView view);
}
