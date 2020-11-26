package com.idnp.musicfit.views.fragments.trainingControllerView;

import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;

public interface iTrainingControllerView {

    public void startTraining();
    public void pauseTraining();
    public void stopTraining();
    public void mapTraining();
    public void viewTrainingReport(Report training);

}
