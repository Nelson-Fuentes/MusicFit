package com.idnp.musicfit.views.fragments.trainingReportView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingReportPresenter.TrainingReportPresenter;
import com.idnp.musicfit.presenter.trainingReportPresenter.iTrainingReportPresenter;


public class TrainingReportFragment extends Fragment implements iTrainingReportView{

    private View view;
    private iTrainingReportPresenter trainingReportPresenter;

    public TrainingReportFragment(Training training) {
        this.trainingReportPresenter = new TrainingReportPresenter(this, training);
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
                        FragmentManager.fragmentManager.remove(TrainingReportFragment.this);
                    }
                });
        return this.view;
    }


}