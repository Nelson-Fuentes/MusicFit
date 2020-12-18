package com.idnp.musicfit.presenter.trainingReportListPresenter;

import com.idnp.musicfit.models.adapters.ReportAdapter;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.trainingService.TrainingHelper;

import com.idnp.musicfit.views.fragments.trainingReportListView.iTrainingReportListView;

import java.util.ArrayList;

public class TrainingReportListPresenter implements iTrainingReportListPresenter {

    private iTrainingReportListView trainingReportListView;
    ReportAdapter myAdapter;
    public TrainingReportListPresenter(iTrainingReportListView trainingReportListView,ReportAdapter adapter){
        this.trainingReportListView = trainingReportListView;
        this.myAdapter=adapter;
    }

    @Override
    public void loadTrainingList(int dayStart,int monthStart, int yearStart,int dayEnd,int monthEnd, int yearEnd) {
        myAdapter.setDataSet(yearStart,monthStart,dayStart,yearEnd,monthEnd,dayEnd);
    }
}
