package com.idnp.musicfit.models.services.trainingService;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;

import java.util.ArrayList;
import java.util.Calendar;

public class TrainingService {

    public static TrainingService trainingService;

    public Training makeTraining(){
        return new Training();
    }

    public void startTraining(Training training){
        training.start();
    }

    public void pauseTraining(Training training){
        training.pause();
    }

    public void stopTraining(Training training){
        training.stop();
    }

    public void saveTraining(Training training){

    }

    public ArrayList<Report> getTrainingList(){
        ArrayList<Report> trainings = new ArrayList<Report>();
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),18.5,"Yanahuara","Cerro Colorado", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),12.5,"Sachaca","Characato", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),19.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),14.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),15.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),17.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),19.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),10.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),4.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));
        trainings.add(new Report(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),9.5,"Miraflores","Quequeña", R.drawable.rp_icon_running));


        return  trainings;
    }
}
