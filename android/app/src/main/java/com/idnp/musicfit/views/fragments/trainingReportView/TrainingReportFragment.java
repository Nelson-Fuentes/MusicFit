package com.idnp.musicfit.views.fragments.trainingReportView;

import android.Manifest;

import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;

import android.os.SystemClock;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.services.trainingService.ReportHelper;
import com.idnp.musicfit.models.services.trainingService.TrainingHelper;
import com.idnp.musicfit.models.services.trainingService.TrainingLocationIntentService;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;

import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingReportPresenter.TrainingReportPresenter;
import com.idnp.musicfit.presenter.trainingReportPresenter.iTrainingReportPresenter;
import com.idnp.musicfit.views.fragments.trainingReportListView.TrainingReportListFragment;



public class TrainingReportFragment extends Fragment implements
        iTrainingReportView,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private View view;
    private iTrainingReportPresenter trainingReportPresenter;
    private ImageButton reportList;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private ImageView button_play_pause, button_stop;//para el control del entrenamiento
    private TrainingNotificationDataView stateTraining;//guarda datos de la notificación sea entrenando o pause
    private boolean isTraining = false;//-----------para verificar si está activo el entrenamiento

    private Chronometer chronometer;
    private long time_gone = 0;
    private long pauseOffSet;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final long delay_buttons = 0;
    private Report report;

    //-----------circle labels

    private ConstraintLayout cl_hour;
    private TextView tv_hour;
    private TextView tv_hour_l;

    private ConstraintLayout cl_min;
    private TextView tv_min;
    private TextView tv_min_l;

    private ConstraintLayout cl_sec;
    private TextView tv_sec;
    private TextView tv_sec_l;

    private ConstraintLayout cl_km;
    private TextView tv_km;
    private TextView tv_km_l;

    private ConstraintLayout cl_cal;
    private TextView tv_cal;
    private TextView tv_cal_l;


    //------------------------------------DATA TO SHOW REPORT------------------------


    public TrainingReportFragment(Report training) {//--------------------------------------------------ok
        this.trainingReportPresenter = new TrainingReportPresenter(this, training);
        this.report=training;
    }

    public void onCreate(Bundle savedInstanceState) {//--------------------------------------------------ok
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {//--------------------------------------------------ok
        if (this.view == null) {
            this.view = inflater.inflate(R.layout.fragment_training_report, container, false);
        }

        //-----------------Circle labels ----------------------

        /*cl_hour=(ConstraintLayout)this.view.findViewById(R.id.cl_11);
        tv_hour=(TextView)this.view.findViewById(R.id.report_view_hour);
        tv_hour_l=(TextView)this.view.findViewById(R.id.label_hour);

        cl_min=(ConstraintLayout)this.view.findViewById(R.id.cl_12);
        tv_min=(TextView)this.view.findViewById(R.id.report_view_min);
        tv_min_l=(TextView)this.view.findViewById(R.id.label_min);

        cl_sec=(ConstraintLayout)this.view.findViewById(R.id.cl_13);
        tv_sec=(TextView)this.view.findViewById(R.id.report_view_sec);
        tv_sec_l=(TextView)this.view.findViewById(R.id.label_sec);

        cl_km=(ConstraintLayout)this.view.findViewById(R.id.cl_2);
        tv_km=(TextView)this.view.findViewById(R.id.report_view_km);
        tv_km_l=(TextView)this.view.findViewById(R.id.label_km);

        cl_cal=(ConstraintLayout)this.view.findViewById(R.id.cl_3);
        tv_cal=(TextView)this.view.findViewById(R.id.report_view_kcal);
        tv_cal_l=(TextView)this.view.findViewById(R.id.label_kcal);*/

        //--------------End Circle labels

        reportList = (ImageButton) this.view.findViewById(R.id.buttonlist);
        button_play_pause = (ImageView) this.view.findViewById(R.id.button_training_play_pause);
        button_stop = (ImageView) this.view.findViewById(R.id.button_training_stop);
        chronometer = (Chronometer) view.findViewById(R.id.chronometer);

        chronometer.setOnChronometerTickListener(eventChrono);
        button_play_pause.setOnClickListener(play_pause_training);
        button_stop.setOnClickListener(stop_training);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setText("00:00:00");

        // this.trainingReportPresenter.loadDataNotificationTraining();//carga los datos de los estados (play training, pause training)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        reportList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.fragmentManager.show(new TrainingReportListFragment());
            }
        });
        return this.view;
    }

    View.OnClickListener play_pause_training = new View.OnClickListener() {//--------------------------------------------------ok
        @Override
        public void onClick(View view) {
            startTrainingService(view);
        }
    };

    View.OnClickListener stop_training = new View.OnClickListener() {//--------------------------------------------------ok
        @Override
        public void onClick(View view) {
            stopTrainingService(view);
        }
    };
    Chronometer.OnChronometerTickListener eventChrono = new Chronometer.OnChronometerTickListener() {//--------------------------------------------------ok
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            long time = SystemClock.elapsedRealtime() - chronometer.getBase();
            int h = (int) (time / 3600000);
            int m = (int) (time - h * 3600000) / 60000;
            int s = (int) (time - h * 3600000 - m * 60000) / 1000;
            String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
            chronometer.setText(t);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {//--------------------------------------------------ok

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, new TrainingMapFragment(report)).commit();
    }

    @Override
    public void onStart() {//--------------------------------------------------ok
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
        stopButtonVisibility();
        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        chronometer.setBase(prefs.getLong("getBase", 0));
        time_gone = SystemClock.elapsedRealtime() - chronometer.getBase();
        isTraining = TrainingHelper.getLocationRequestStatus(getContext());
        pauseOffSet = prefs.getLong("pauseOffSet", 0);

        if (isTraining) {
            pauseOffSet = 0;
            startTrainingService(view);
        } else {
            time_gone = -100;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pauseTrainingService(view);
                }
            }, 100);
        }
        time_gone = 0;
        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putLong("time_gone", 0);
        editor.apply();

    }

    @Override
    public void onStop() {//--------------------------------------------------ok
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
        fusedLocationProviderClient.removeLocationUpdates(getPendingIntent());//---------------location update stopped intent service

        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putLong("time_gone", SystemClock.elapsedRealtime());
        editor.putLong("getBase", chronometer.getBase());
        editor.putLong("pauseOffSet", pauseOffSet);
        editor.putBoolean("running", isTraining);
        editor.apply();
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(getContext(), TrainingLocationIntentService.class);
        intent.setAction(TrainingLocationIntentService.ACTION_PROCESS_UPDATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            return PendingIntent.getService(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }




    public void startTrainingService(View view){//--------------------------------------------------ok

        if(TrainingHelper.getLocationRequestStatus(getContext())){
            pauseTrainingService(view);
        }else{
            boolean bol=ReportHelper.getStartIdTrainingShared (getContext()).equals(ReportHelper.NONE_START_ID);
            if(bol){
                ReportHelper.startTrainingVarsShared(getContext());
                button_stop.setVisibility(View.VISIBLE);//----------para que se actualice en myPos el valor de la posición inicial
            }
            requestLocationUpdate();//------------------start update location service intent
            TrainingHelper.setLocationRequestStatus(getContext(),TrainingHelper.TRAINING);
            setButtonVisibleState(true);
            chronoPlay();//--
        }
    }

    @Override
    public void pauseTrainingService(View view) {
        //fusedLocationProviderClient.removeLocationUpdates(getPendingIntent());
        ReportHelper.pauseTrainingVarsShared(getContext());
        setButtonVisibleState(false);
        chronoPause();
    }
    private void startEndLocation() {//-----------PARA GUARDAR EL INICIO DE ENTRENAMIENTO
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if(location!=null){
                    ReportHelper.stopTrainingVarsShared(getContext(),""+location.getLatitude()+"/"+location.getLongitude());
                }
            }
        });
    }
    public void stopTrainingService(View view){//--------------------------------------------------ok

        TrainingHelper.setLocationRequestStatus(getContext(),TrainingHelper.NO_TRAINING);
        startEndLocation();
        setButtonVisibleState(false);
        chronoStop();
        button_stop.setVisibility(View.INVISIBLE);

    }

    private void stopButtonVisibility(){//-----------------------------------ok
//        boolean bol=!ReportHelper.getStartIdTrainingShared(getContext()).equals(ReportHelper.NONE_START_ID);
//
//        if(bol){
            button_stop.setVisibility(View.VISIBLE);
            chronometer.setVisibility(View.VISIBLE);
//        }
    }

    private void chronoPlay(){
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet - time_gone);
        chronometer.start();
        chronometer.setVisibility(View.VISIBLE);

    }
    private void chronoPause(){
        chronometer.stop();
        pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
    }
    private void chronoStop(){
        time_gone = 0;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffSet = 0;
        chronometer.setText("00:00:00");

        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putLong("pauseOffSet", 0);
        editor.apply();
        chronometer.setVisibility(View.INVISIBLE);
    }


    private void requestLocationUpdate(){//--------------------------------------------------ok


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);//secc to refresh location
        locationRequest.setFastestInterval(4000);
        locationRequest.setMaxWaitTime(10*1000);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }


    @Override
    public void onPause() {//--cuando salimos del fragment hace unbindService y luego desconecta el servicio llamando a onServiceDisconnected
        super.onPause();
     //   fusedLocationProviderClient.removeLocationUpdates(getPendingIntent());
//        this.getActivity().unbindService(this);
    }
    @Override
    public void onResume() {//--------------------------------------------------ok
        super.onResume();
        //output.setText(TrainingHelper.getSavedLocationResults(this))
        setButtonVisibleState(TrainingHelper.getLocationRequestStatus(getContext()));
        stopButtonVisibility();
    }

    private void setButtonVisibleState(boolean isTraining){//--------------------------------------------------ok
        if(isTraining){
            button_play_pause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);//cambia ícono
        }else{
            button_play_pause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);//cambia ícono
        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key){//--------------------------------------------------ok
        if(key.equals(TrainingHelper.KEY_LAST_LOCATION_SHARED)){
            //moutput.setText(TrainingHelper.getSavedLocationResults(getContext()));
        }else if(key.equals(TrainingHelper.KEY_IS_TRAINING_SHARED)){
            setButtonVisibleState(TrainingHelper.getLocationRequestStatus(getContext()));
        }
    }

    private void setModeReportView(boolean isModeViewReport){
        if(isModeViewReport){
            cl_hour.setVisibility(View.VISIBLE);
            tv_hour.setVisibility(View.VISIBLE);
            tv_hour_l.setVisibility(View.VISIBLE);

            cl_min.setVisibility(View.VISIBLE);
            tv_min.setVisibility(View.VISIBLE);
            tv_min_l.setVisibility(View.VISIBLE);

            cl_sec.setVisibility(View.VISIBLE);
            tv_sec.setVisibility(View.VISIBLE);
            tv_sec_l.setVisibility(View.VISIBLE);

            button_play_pause.setVisibility(View.INVISIBLE);
            button_stop.setVisibility(View.INVISIBLE);
            chronometer.setVisibility(View.INVISIBLE);

        }
    }


}