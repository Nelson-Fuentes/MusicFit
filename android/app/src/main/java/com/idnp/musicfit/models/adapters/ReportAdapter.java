package com.idnp.musicfit.models.adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.views.fragments.trainingReportListView.TrainingReportListFragment;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends FirebaseRecyclerAdapter<Report,ReportAdapter.ReportHolder> {

    private Geocoder geocoder;
    private int yearStart;
    private int monthStart;
    private int dayStart;
    private int yearEnd;
    private int monthEnd;
    private int dayEnd;
    private boolean filter=false;

    public ReportAdapter(@NonNull FirebaseRecyclerOptions<Report> options,Context context) {

        super(options);

        geocoder=new Geocoder(context);

    }


    public void setDataSet(int yearStart, int monthStart, int dayStart,int yearEnd, int monthEnd, int dayEnd){
        this.yearStart=yearStart;
        this.monthStart=monthStart;
        this.dayStart=dayStart;
        this.yearEnd=yearEnd;
        this.monthEnd=monthEnd;
        this.dayEnd=dayEnd;
        filter=true;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_training_list,parent,false);
        return new ReportHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReportHolder holder, int position, @NonNull Report report) {
        boolean condStart = false;
        boolean condEnd = false;
        if (filter) {

            if (report.getStartYear() == yearStart) {
                if (report.getStartMonth() == monthStart) {
                    if (report.getStartDay() >= dayStart)
                        condStart = true;
                } else if (report.getStartMonth() > monthStart) {
                    condStart = true;
                }
            } else if (report.getStartYear() > yearStart) {
                condStart = true;
            }

            if (report.getStartYear() == yearEnd) {
                if (report.getStartMonth() == monthEnd) {
                    if (report.getStartDay() <= dayEnd)
                        condEnd = true;
                } else if (report.getStartMonth() < monthEnd) {
                    condEnd = true;
                }
            } else if (report.getStartYear() > yearEnd) {
                condEnd = true;
            }
        }

        if(!filter||(condStart&&condEnd)){

            String startPlaceName="";
            String endPlaceName="";
            try {
                List<Address> addresses = geocoder.getFromLocation(report.getStartP().latitude, report.getStartP().longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    startPlaceName = address.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                List<Address> addresses = geocoder.getFromLocation(report.getEndP().latitude, report.getEndP().longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    endPlaceName = address.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.startplace.setText(startPlaceName);
            holder.endplace.setText(endPlaceName);

            String month= ""+report.getStartMonth();
            String year= ""+report.getStartYear();
            String day= ""+report.getStartDay();
            String sec= ""+report.getStartSec();
            String min=""+report.getStartMin();
            String hour =""+report.getStartHour();

            holder.dateday.setText(day +"/"+month+"/"+year);
            holder.datehour.setText(hour+":"+min+":"+sec);

            //obtener los datos de los tramos de  rutas que hizo este Reporte, ya que de esta forma calcularemos el recorrido
            //y a la vez

            if(report.getDurationHour()*60+report.getDurationMin()>5){
                holder.iconrun.setImageResource(R.drawable.rp_icon_running);
            }else{
                holder.iconrun.setImageResource(R.drawable.rp_icon_running_slow);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {//al presionar el item de la lista de reportes
                @Override
                public void onClick(View view) {
                    FragmentManager.fragmentManager.changeFragment( new TrainingReportFragment(report));
                }
            });
        }
    }


    public static  class ReportHolder extends  RecyclerView.ViewHolder{
        private TextView dateday;
        private TextView datehour;
        private TextView startplace;
        private TextView endplace;
        private ImageView iconrun;
        public ReportHolder(@NonNull View reportItemview){
            super(reportItemview);
            dateday=reportItemview.findViewById(R.id.value_rep_date_day);
            datehour=reportItemview.findViewById(R.id.value_rep_date_hour);
            startplace=reportItemview.findViewById(R.id.value_rep_from_place);
            endplace=reportItemview.findViewById(R.id.value_rep_to_place);
            iconrun=reportItemview.findViewById(R.id.value_rep_icon);
        }
    }
}
