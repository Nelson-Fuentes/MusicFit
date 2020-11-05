package com.idnp.musicfit.views.fragments.trainingReportListView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.adapters.ReportAdapter;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingReportListPresenter.TrainingReportListPresenter;
import com.idnp.musicfit.presenter.trainingReportListPresenter.iTrainingReportListPresenter;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;

import java.util.ArrayList;

public class TrainingReportListFragment extends Fragment implements iTrainingReportListView {

    private View view;
    private ReportAdapter reportAdapter;
    private RecyclerView reportListView;
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


        //vinculando el recyclerview
        this.reportListView = view.findViewById(R.id.training_list);

        LinearLayoutManager manager=new LinearLayoutManager(this.getContext());
        reportListView.setLayoutManager(manager);
        //crear el adaptador
        this.reportAdapter = new ReportAdapter();
        //agrega adaptador
        this.reportListView.setAdapter(this.reportAdapter);

        this.trainingReportListPresenter = new TrainingReportListPresenter(this);
        this.trainingReportListPresenter.loadTrainingList();

       /*
        this.trainingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager.fragmentManager.changeFragment( new TrainingReportFragment(TrainingReportListFragment.this.trainingAdapter.getItem(position)));
            }
        });*/

    }

    @Override
    public void showReportList(ArrayList<Report> reportes) {
        this.reportAdapter.setDataSet(reportes);
    }
}