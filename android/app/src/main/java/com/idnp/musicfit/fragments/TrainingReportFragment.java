package com.idnp.musicfit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.Training;


public class TrainingReportFragment extends Fragment {
    private Training training;
    private View view;

    public TrainingReportFragment(Training training) {
        this.training = training;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view = inflater.inflate(R.layout.fragment_training_report, container, false);
        }
        Button button = (Button) this.view.findViewById(R.id.buttonExit);
        button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager.fragmentManager.popBackStack();
                    }
                });

        return this.view;
    }
}