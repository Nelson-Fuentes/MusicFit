package com.idnp.musicfit.presenter.trainingReportPresenter;

import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;

public class TrainingReportPresenter implements iTrainingReportPresenter {

    private iTrainingReportView trainingReportView;
    private Training training;

    public TrainingReportPresenter(iTrainingReportView trainingReportView, Training training){
        this.training = training;
        this.trainingReportView = trainingReportView;
    }

}
