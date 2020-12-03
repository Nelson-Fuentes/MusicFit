package com.idnp.musicfit.views.fragments.musicPlayerControllerView;

public interface iMusicPlayerControllerView {

    public void loadSelectedMusic();
    public void play();
    public void pause();
    public void stop();
    public void random();
    public void repeat(boolean state);
    public void back();
    public void advance();
}
