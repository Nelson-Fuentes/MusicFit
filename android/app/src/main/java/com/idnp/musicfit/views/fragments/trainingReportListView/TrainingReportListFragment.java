package com.idnp.musicfit.views.fragments.trainingReportListView;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.adapters.ReportAdapter;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.presenter.trainingReportListPresenter.TrainingReportListPresenter;
import com.idnp.musicfit.presenter.trainingReportListPresenter.iTrainingReportListPresenter;
import com.idnp.musicfit.views.activities.mainView.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrainingReportListFragment extends Fragment implements iTrainingReportListView {

    private View view;
    private ReportAdapter reportAdapter;
    private RecyclerView reportListView;
    private iTrainingReportListPresenter trainingReportListPresenter;



    //datepicker

    TextView label_date_ini;
    TextView button_date_ini;
    TextView label_date_end;
    TextView button_date_end;

    DatePickerDialog.OnDateSetListener setListener;

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

        button_date_ini=view.findViewById(R.id.bpicker_top_ini);
        button_date_end=view.findViewById(R.id.bpicker_top_end);
        label_date_ini=view.findViewById(R.id.tvdatepicker_top_ini);
        label_date_end=view.findViewById(R.id.tvdatepicker_top_end);
        //vinculando el recyclerview
        this.reportListView = view.findViewById(R.id.training_list);

        Calendar cal= Calendar.getInstance();
        final int day=cal.get(Calendar.DAY_OF_MONTH);
        final int month=cal.get(Calendar.MONTH);
        final int year=cal.get(Calendar.YEAR);

        button_date_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String dateformat=day+"/"+month+"/"+year;
                        label_date_ini.setText(dateformat);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        button_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String dateformat=day+"/"+month+"/"+year;
                        label_date_end.setText(dateformat);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
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


        /*load top filter components*/


    }

    @Override
    public void showReportList(ArrayList<Report> reportes) {
        this.reportAdapter.setDataSet(reportes);
    }
}