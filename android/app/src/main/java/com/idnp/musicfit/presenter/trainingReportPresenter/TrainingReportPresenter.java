package com.idnp.musicfit.presenter.trainingReportPresenter;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;

public class TrainingReportPresenter implements iTrainingReportPresenter {

    private iTrainingReportView trainingReportView;
    private Report training;

    public TrainingReportPresenter(iTrainingReportView trainingReportView, Report training){
        this.training = training;
        this.trainingReportView = trainingReportView;
    }


    @Override
    public void loadDataNotificationTraining() {
        trainingReportView.startDataNotificationTraining(
                new TrainingNotificationDataView(TrainingNotificationDataView.STATE_PLAY_TRAINING_LABEL, R.drawable.rp_icon_running));
    }

    @Override
    public void updateDataNotificationTraining(boolean stateTraining) {
        if(stateTraining)
            this.trainingReportView.updateDataNotificationTrainingState(TrainingNotificationDataView.STATE_PLAY_TRAINING_LABEL,R.drawable.rp_icon_running);
        else
            this.trainingReportView.updateDataNotificationTrainingState(TrainingNotificationDataView.STATE_PAUSE_TRAINING_LABEL,R.drawable.rp_icon_running_slow);

    }

}
