package com.idnp.musicfit.views.fragments.trainingControllerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingControllerPresenter.TrainingControllerPresenter;
import com.idnp.musicfit.presenter.trainingControllerPresenter.iTrainingControllerPresenter;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;


public class TrainingControllerFragment extends Fragment implements iTrainingControllerView {

    private View view;
    private iTrainingControllerPresenter trainingControllerPresenter;

    public TrainingControllerFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view = inflater.inflate(R.layout.fragment_training_controller, container, false);
        }
        this.trainingControllerPresenter = new TrainingControllerPresenter(this);
        Button button = (Button) view.findViewById(R.id.buttonReport);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingControllerFragment.this.trainingControllerPresenter.stopTraining();
            }
        });
        return this.view;
    }

    private void startReport(){
    }

    @Override
    public void startTraining() {

    }

    @Override
    public void pauseTraining() {

    }

    @Override
    public void stopTraining() {

    }

    @Override
    public void viewTrainingReport(Training training) {
        FragmentManager.fragmentManager.changeFragment(new TrainingReportFragment(training));
    }
}