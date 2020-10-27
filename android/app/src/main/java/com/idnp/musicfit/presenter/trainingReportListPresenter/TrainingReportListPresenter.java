package com.idnp.musicfit.presenter.trainingReportListPresenter;

import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.trainingService.TrainingService;
import com.idnp.musicfit.views.fragments.trainingReportListView.iTrainingReportListView;

import java.util.ArrayList;

public class TrainingReportListPresenter implements iTrainingReportListPresenter {

    private iTrainingReportListView trainingReportListView;


    public TrainingReportListPresenter(iTrainingReportListView trainingReportListView){
        this.trainingReportListView = trainingReportListView;
    }

    @Override
    public void loadTrainingList() {
        this.trainingReportListView.showReportList(TrainingService.trainingService.getTrainingList());
    }
}
