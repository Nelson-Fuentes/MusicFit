package com.idnp.musicfit.models.services.trainingService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrainingService extends Service {
    private IBinder myTrainingBinder;
    public static final String CHANNEL_ID_1="CHANNEL_1";
    public static final String CHANNEL_ID_2="CHANNEL_2";
    public static final String ACTION_PLAY="PLAY";
    public static final String ACTION_STOP="STOP";

    iTrainingReportView actionTraining;
    public static TrainingService trainingService;

    public class TrainingIBinder extends Binder {
        public TrainingService getService(){
            return TrainingService.this;
        }
    }



    public Report makeTraining(){ // para guardar el reporte
        //return new Report(23,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        return null;
    }
    public void startTraining(Report training){
        training.start();
    }

    public void pauseTraining(Report training){
        training.pause();
    }

    public void stopTraining(Report training){
        training.stop();
    }

    public void saveTraining(Report training){ }

    public ArrayList<Report> getTrainingList(){
        //obtener la lista de entrenamientos desde la base de datos en nube

        ArrayList<Report> trainings = new ArrayList<Report>();

        /*Report nuevo = new Report(19,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo1 = new Report(12,10,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo1.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo2 = new Report(9,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo2.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo3 = new Report(15,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo3.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo4 = new Report(23,10,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo4.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo5 = new Report(15,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo5.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo6 = new Report(23,9,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo6.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo7 = new Report(30,2,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo7.setEndP(new LatLng(-16.4356583,-71.5651415));

        trainings.add(nuevo);
        trainings.add(nuevo1);
        trainings.add(nuevo2);
        trainings.add(nuevo3);
        trainings.add(nuevo4);
        trainings.add(nuevo5);
        trainings.add(nuevo6);
        trainings.add(nuevo7);*/

        return  trainings;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myTrainingBinder;
    }
    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        String actionName=intent.getStringExtra("myActionName");
        if(actionName!=null){
            switch (actionName){
                case ACTION_PLAY:
                    if(actionTraining!=null){
                        //actionTraining.startTraining();
                    }
                    break;
                case ACTION_STOP:
                    if(actionTraining!=null){
                        //actionTraining.stopTraining();
                    }
                    break;
            }
        }


        return START_STICKY;
        //return super.onStartCommand(intent,flags,startId);
    }

    public void setCallback(iTrainingReportView actionTraining){
        this.actionTraining=actionTraining;
    }
}
