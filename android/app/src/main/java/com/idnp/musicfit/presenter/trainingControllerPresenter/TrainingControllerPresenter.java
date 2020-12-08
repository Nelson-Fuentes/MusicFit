package com.idnp.musicfit.presenter.trainingControllerPresenter;

import android.content.Context;

import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;


import com.idnp.musicfit.models.services.trainingService.DBManager;



import com.idnp.musicfit.views.fragments.trainingControllerView.iTrainingControllerView;

import java.util.ArrayList;

public class TrainingControllerPresenter  implements iTrainingControllerPresenter{

    private iTrainingControllerView trainingControllerView;
    private Report training;

 //  private DBManager dbManager;

    public TrainingControllerPresenter(iTrainingControllerView trainingControllerView){
        this.trainingControllerView = trainingControllerView;
    //    this.training = TrainingService.trainingService.makeTraining();
    }

    @Override
    public void startTraining() {

        //this.trainingControllerView.startTraining();
       // TrainingService.trainingService.startTraining(this.training);

    }

    @Override
    public void pauseTraining() {

        //this.trainingControllerView.pauseTraining();
        //TrainingService.trainingService.pauseTraining(this.training);

    }

    @Override
    public void stopTraining() {
       // this.trainingControllerView.stopTraining();
      //  TrainingService.trainingService.stopTraining(this.training);
        this.trainingControllerView.viewTrainingReport(this.training);
    }

    @Override
    public ArrayList testDB(Context c, String name) {
     //   dbManager = new DBManager(c);
        //dbManager.deleteDB(c);
        /*dbManager.open();

        dbManager.insert(name);
        dbManager.close();
        ArrayList<String> aux = dbManager.getElements();
*/
        return null;
    }


}
