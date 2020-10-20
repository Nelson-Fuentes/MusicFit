package com.idnp.musicfit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.Training;


public class TrainingControllerFragment extends Fragment {

    private NavController navHostFragment;
    private View view;
    private Training training;

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
        if (this.training == null){
            this.training = new Training();
        }
        Button button = (Button) view.findViewById(R.id.buttonReport);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingControllerFragment.this.startReport();
            }
        });
        return this.view;
    }

    private void startReport(){
        FragmentManager.fragmentManager.changeFragment(new TrainingReportFragment(this.training));
    }

}