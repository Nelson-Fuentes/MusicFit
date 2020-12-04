package com.idnp.musicfit.presenter.trainingReportPresenter;

public interface iTrainingReportPresenter {
    public  void startTraining();
    public  void pauseTraining();
    public  void stopTraining();
    public void loadDataNotificationTraining();
    public void updateDataNotificationTraining(boolean stateTraining);
}
