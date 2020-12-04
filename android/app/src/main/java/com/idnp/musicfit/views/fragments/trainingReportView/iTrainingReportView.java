package com.idnp.musicfit.views.fragments.trainingReportView;

import android.view.View;

import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;

public interface iTrainingReportView {
  //  public void showNotification(int playPauseButton);
//    public void startDataNotificationTraining(TrainingNotificationDataView stateTraining);
//    public void updateDataNotificationTrainingState(String stateTrainingTitle,int backgroundTrainingState);

    public void startTrainingService(View view);
    public void pauseTrainingService(View view);
    public void stopTrainingService(View view);

}
