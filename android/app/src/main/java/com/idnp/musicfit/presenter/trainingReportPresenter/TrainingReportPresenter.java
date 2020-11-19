package com.idnp.musicfit.presenter.trainingReportPresenter;

import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;

public class TrainingReportPresenter implements iTrainingReportPresenter {

    private iTrainingReportView trainingReportView;
    private Report training;

    public TrainingReportPresenter(iTrainingReportView trainingReportView, Report training){
        this.training = training;
        this.trainingReportView = trainingReportView;
    }

}
