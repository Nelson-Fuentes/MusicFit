package com.idnp.musicfit.views.fragments.trainingReportListView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.adapters.TrainingAdapter;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingReportListPresenter.TrainingReportListPresenter;
import com.idnp.musicfit.presenter.trainingReportListPresenter.iTrainingReportListPresenter;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;

import java.util.ArrayList;

public class TrainingReportListFragment extends Fragment implements iTrainingReportListView {

    private View view;
    private TrainingAdapter trainingAdapter;
    private ListView trainingListView;
    private iTrainingReportListPresenter trainingReportListPresenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view  = inflater.inflate(R.layout.fragment_training_report_list, container, false);
            this.loadComponents(this.view);
        }
        return  this.view;

    }

    private  void loadComponents(View view){
        this.trainingAdapter = new TrainingAdapter(this.getContext());
        this.trainingListView = (ListView) view.findViewById(R.id.training_list);
        this.trainingListView.setAdapter(this.trainingAdapter);
        this.trainingReportListPresenter = new TrainingReportListPresenter(this);
        this.trainingReportListPresenter.loadTrainingList();
        this.trainingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager.fragmentManager.changeFragment( new TrainingReportFragment(TrainingReportListFragment.this.trainingAdapter.getItem(position)));
            }
        });

    }

    @Override
    public void showReportList(ArrayList<Training> trainings) {
        this.trainingAdapter.setDataSet(trainings);
    }
}