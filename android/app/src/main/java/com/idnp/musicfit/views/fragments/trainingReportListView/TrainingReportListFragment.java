package com.idnp.musicfit.views.fragments.trainingReportListView;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
    ImageButton button_date_ini;
    TextView label_date_end;
    ImageButton button_date_end;

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

    private Calendar cal= Calendar.getInstance();

    final int day=cal.get(Calendar.DAY_OF_MONTH);
    final int month=cal.get(Calendar.MONTH);
    final int year=cal.get(Calendar.YEAR);
    //evento de click al date picker inicial
    View.OnClickListener onClickDateIni= new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month=month+1;
                    String dateformat=day+"/"+month+"/"+year;
                    label_date_ini.setText(dateformat);
                    String finaldate[]=label_date_end.getText().toString().split("/");
                    trainingReportListPresenter.loadTrainingList(day,month,year,Integer.parseInt(finaldate[0]),Integer.parseInt(finaldate[1]),Integer.parseInt(finaldate[2]));
                }
            },year,month,day);
            datePickerDialog.show();
        }
    };
    //evento de click al date picker final
    private View.OnClickListener onClickDateEnd= new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month=month+1;
                    String dateformat=day+"/"+month+"/"+year;
                    label_date_end.setText(dateformat);
                    String startdate[]=label_date_ini.getText().toString().split("/");
                    trainingReportListPresenter.loadTrainingList(Integer.parseInt(startdate[0]),Integer.parseInt(startdate[1]),Integer.parseInt(startdate[2]),day,month,year);

                }
            },year,month,day);
            datePickerDialog.show();
        }
    };
    //--------------------------------------------------CARGAR LOS COMPONENTES ---------------------------
    private  void loadComponents(View view){
        //inicializando botones y labels
        button_date_ini=view.findViewById(R.id.bpicker_top_ini);
        button_date_end=view.findViewById(R.id.bpicker_top_end);
        label_date_ini=view.findViewById(R.id.tvdatepicker_top_ini);
        label_date_end=view.findViewById(R.id.tvdatepicker_top_end);

        label_date_end.setText(""+day+"/"+(month+1)+"/"+year);
        label_date_ini.setText(""+day+"/"+(month)+"/"+year);

        //dandole eventos a los botones
        button_date_ini.setOnClickListener(onClickDateIni);//date picker inicial evento
        button_date_end.setOnClickListener(onClickDateEnd);//date picker final evento
        //vinculando el recyclerview
        this.reportListView = view.findViewById(R.id.training_list);

        LinearLayoutManager manager=new LinearLayoutManager(this.getContext());
        reportListView.setLayoutManager(manager);
        //crear el adaptador
        this.reportAdapter = new ReportAdapter(this.getContext());
        //agrega adaptador
        this.reportListView.setAdapter(this.reportAdapter);
        this.trainingReportListPresenter = new TrainingReportListPresenter(this);//crea el presentador de esta clase
        this.trainingReportListPresenter.loadTrainingList(day,month,year,day,month+1,year);//carga la lista de reportes de entrenamiento
    }
    @Override
    public void showReportList(ArrayList<Report> reportes) {
        this.reportAdapter.setDataSet(reportes);
    }
}