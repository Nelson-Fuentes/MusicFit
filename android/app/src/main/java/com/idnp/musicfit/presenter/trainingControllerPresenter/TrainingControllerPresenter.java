package com.idnp.musicfit.presenter.trainingControllerPresenter;

import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.trainingService.TrainingService;
import com.idnp.musicfit.views.fragments.trainingControllerView.iTrainingControllerView;

public class TrainingControllerPresenter  implements iTrainingControllerPresenter{

    private iTrainingControllerView trainingControllerView;
    private Report training;

    public TrainingControllerPresenter(iTrainingControllerView trainingControllerView){
        this.trainingControllerView = trainingControllerView;
        this.training = TrainingService.trainingService.makeTraining();
    }

    @Override
    public void startTraining() {
        this.trainingControllerView.startTraining();
        TrainingService.trainingService.startTraining(this.training);
    }

    @Override
    public void pauseTraining() {
        this.trainingControllerView.pauseTraining();
        TrainingService.trainingService.pauseTraining(this.training);
    }

    @Override
    public void stopTraining() {
        this.trainingControllerView.stopTraining();
        TrainingService.trainingService.stopTraining(this.training);
        this.trainingControllerView.viewTrainingReport(this.training);
    }
}
