package com.idnp.musicfit.presenter.trainingControllerPresenter;

import android.content.Context;

import java.util.ArrayList;

public interface iTrainingControllerPresenter {

    public  void startTraining();
    public  void pauseTraining();
    public  void stopTraining();
    public ArrayList testDB(Context c, String name);

}
