package com.idnp.musicfit.models.services.trainingService;

import com.idnp.musicfit.models.entities.Training;

import java.util.ArrayList;

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

    public ArrayList<Training> getTrainingList(){
        ArrayList<Training> trainings = new ArrayList<Training>();
        trainings.add(new Training());
        trainings.add(new Training());
        return  trainings;
    }
}
