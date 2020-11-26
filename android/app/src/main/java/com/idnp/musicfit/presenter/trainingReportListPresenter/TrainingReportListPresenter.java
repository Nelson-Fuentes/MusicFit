package com.idnp.musicfit.presenter.trainingReportListPresenter;

import com.idnp.musicfit.models.entities.Report;
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
    public void loadTrainingList(int dayStart,int monthStart, int yearStart,int dayEnd,int monthEnd, int yearEnd) {
        ArrayList<Report> reports=TrainingService.trainingService.getTrainingList();
        ArrayList<Report> returnReports= new ArrayList<Report>();

        System.out.println("("+dayStart+","+monthStart+","+yearStart+"),("+dayEnd+","+monthEnd+","+yearEnd+")");


        for(int i=0;i<reports.size();i++){
            Report current=reports.get(i);
            boolean condStart=false;
            if(current.getStartYear()==yearStart){
                if(current.getStartMonth()==monthStart){
                    if(current.getStartDay()>=dayStart)
                        condStart=true;
                }else if(current.getStartMonth()>monthStart){
                    condStart=true;
                }
            }else if(current.getStartYear()>yearStart){
                    condStart=true;
            }

            boolean condEnd=false;
            if(current.getStartYear()==yearEnd){
                if(current.getStartMonth()==monthEnd){
                    if(current.getStartDay()<=dayEnd)
                        condEnd=true;
                }else if(current.getStartMonth()<monthEnd){
                    condEnd=true;
                }
            }else if(current.getStartYear()>yearEnd){
                    condEnd=true;
            }
            if(condStart&&condEnd){
                returnReports.add(current);
            }
        }
        this.trainingReportListView.showReportList(returnReports);//filtro
    }
}
