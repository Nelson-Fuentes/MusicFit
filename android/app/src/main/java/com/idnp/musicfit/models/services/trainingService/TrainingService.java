package com.idnp.musicfit.models.services.trainingService;

import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrainingService {

    public static TrainingService trainingService;

    public Report makeTraining(){ // para guardar el reporte
        return new Report(23,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
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

        Report nuevo = new Report(19,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
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
        trainings.add(nuevo7);

        return  trainings;
    }
}
