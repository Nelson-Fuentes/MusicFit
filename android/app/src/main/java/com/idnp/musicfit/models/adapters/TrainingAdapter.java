package com.idnp.musicfit.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Training;

import java.util.ArrayList;

public class TrainingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Training> trainings;

    public  TrainingAdapter(Context context){
        this.context = context;
        this.trainings = new ArrayList<Training>();
    }

    public void  setDataSet(ArrayList<Training> trainings){
        this.trainings = trainings;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.trainings.size();
    }

    @Override
    public Training getItem(int position) {
        return this.trainings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.trainings.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.list_item_training_list, null);
        TextView component = (TextView) view.findViewById(R.id.training_count);
        component.setText("Training: " + position);
        return view;
    }
}
