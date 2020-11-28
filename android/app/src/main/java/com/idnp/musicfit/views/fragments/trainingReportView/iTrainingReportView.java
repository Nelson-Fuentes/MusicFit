package com.idnp.musicfit.views.fragments.trainingReportView;

import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;

public interface iTrainingReportView {
    public void showNotification(int playPauseButton);
    public void startDataNotificationTraining(TrainingNotificationDataView stateTraining);
    public void updateDataNotificationTrainingState(String stateTrainingTitle,int backgroundTrainingState);

    public void startTraining();
    public void pauseTraining();
    public void stopTraining();

}
