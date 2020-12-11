package com.idnp.musicfit.presenter.musicPlayerPresenter;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerNotificationDataView;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.iMusicPlayerControllerView;

public class MusicPlayerPresenter implements iMusicPlayerPresenter{
    private iMusicPlayerControllerView musicPlayerControllerView;
    private Song song;

    public MusicPlayerPresenter(iMusicPlayerControllerView musicPlayerControllerView, Song song){
        this.song = song;
        this.musicPlayerControllerView=musicPlayerControllerView;
    }


    @Override
    public void loadDataNotificationMusicPlayer() {
        /*musicPlayerControllerView.startDataNotificationTraining(
                new TrainingNotificationDataView(TrainingNotificationDataView.STATE_PLAY_TRAINING_LABEL, R.drawable.rp_icon_running));*/
    }

    @Override
    public void updateDataNotificationMusicPlayer(boolean statePlayer) {
        /*if(statePlayer)
            this.musicPlayerControllerView.updateDataNotificationTrainingState(MusicPlayerNotificationDataView.STATE_PLAY,R.drawable.rp_icon_running);
        else
            this.musicPlayerControllerView.updateDataNotificationTrainingState(MusicPlayerNotificationDataView.STATE_PAUSE,R.drawable.rp_icon_running_slow);*/

    }
}
