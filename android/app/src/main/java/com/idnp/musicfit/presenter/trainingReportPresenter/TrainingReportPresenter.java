package com.idnp.musicfit.presenter.trainingReportPresenter;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;

public class TrainingReportPresenter implements iTrainingReportPresenter {

    private iTrainingReportView trainingReportView;


    public TrainingReportPresenter(iTrainingReportView trainingReportView){

        this.trainingReportView = trainingReportView;
    }


    @Override
    public void startTraining() {

    }

    @Override
    public void pauseTraining() {

    }

    @Override
    public void stopTraining() {

    }

   @Override
    public void loadDataNotificationTraining() {
//        trainingReportView.startDataNotificationTraining(
//                new TrainingNotificationDataView(TrainingNotificationDataView.STATE_PLAY_TRAINING_LABEL, R.drawable.rp_icon_running));
    }

    @Override
    public void updateDataNotificationTraining(boolean stateTraining) {
//        if(stateTraining)
//            this.trainingReportView.updateDataNotificationTrainingState(TrainingNotificationDataView.STATE_PLAY_TRAINING_LABEL,R.drawable.rp_icon_running);
//        else
//            this.trainingReportView.updateDataNotificationTrainingState(TrainingNotificationDataView.STATE_PAUSE_TRAINING_LABEL,R.drawable.rp_icon_running_slow);

    }

}
