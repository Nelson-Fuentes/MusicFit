package com.idnp.musicfit.views.fragments.musicPlayerControllerView;

import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerNotificationDataView;


public interface iMusicPlayerControllerView {
    public void showNotification(int playPauseButton);
    public void startDataNotificationMusicPlayer(MusicPlayerNotificationDataView stateTraining);
    public void updateDataNotificationMusicPlayer(String stateTrainingTitle,int backgroundTrainingState);


    public void loadSelectedMusic();
    public void play();
    public void pause();
    public void stop();
    public void random();
    public void repeat(boolean state);
    public void back();
    public void advance();
}
