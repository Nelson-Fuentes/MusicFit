package com.idnp.musicfit.presenter.musicPlayerPresenter;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.iMusicPlayerControllerView;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;

public class MusicPlayerPresenter implements iMusicPlayerPresenter{
    private iMusicPlayerControllerView musicPlayerControllerView;
    private Song song;

    public MusicPlayerPresenter(iMusicPlayerControllerView musicPlayerControllerView, Song song){
        this.song = song;
        this.musicPlayerControllerView=musicPlayerControllerView;
    }


    @Override
    public void loadDataNotificationMusicPlayer() {
        /*trainingReportView.startDataNotificationTraining(
                new TrainingNotificationDataView(TrainingNotificationDataView.STATE_PLAY_TRAINING_LABEL, R.drawable.rp_icon_running));*/
    }

    @Override
    public void updateDataNotificationMusicPlayer(boolean statePlayer) {
        /*if(statePlayer)
            this.trainingReportView.updateDataNotificationTrainingState(TrainingNotificationDataView.STATE_PLAY_TRAINING_LABEL,R.drawable.rp_icon_running);
        else
            this.trainingReportView.updateDataNotificationTrainingState(TrainingNotificationDataView.STATE_PAUSE_TRAINING_LABEL,R.drawable.rp_icon_running_slow);*/

    }
}
