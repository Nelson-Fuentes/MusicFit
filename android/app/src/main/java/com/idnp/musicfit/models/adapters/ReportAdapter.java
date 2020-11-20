package com.idnp.musicfit.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.views.fragments.trainingReportListView.TrainingReportListFragment;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {

    private ArrayList<Report> reportes;

    public ReportAdapter() {
        this.reportes = new ArrayList<Report>();
    }

    public void setDataSet(ArrayList<Report> reportes){
        this.reportes=reportes;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_training_list,parent,false);
        return new ReportHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        Report report = reportes.get(position);
        holder.endplace.setText(report.getPlaceEnd());
        holder.startplace.setText(report.getPlaceStart());
        Date date=report.getStartTraining();
        String month= "11";
        String year= "2020";
        String day= "02";
        holder.dateday.setText(day +"/"+month+"/"+year);

        String hour="11";
        String ampm="am";
        String min="03";
        holder.datehour.setText(hour+":"+min+" "+ampm);
        holder.iconrun.setImageResource(report.getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager.fragmentManager.changeFragment( new TrainingReportFragment(report));
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportes.size();
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
