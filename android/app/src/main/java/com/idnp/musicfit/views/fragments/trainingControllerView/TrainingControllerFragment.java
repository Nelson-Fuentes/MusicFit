package com.idnp.musicfit.views.fragments.trainingControllerView;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.dbmodels.DBModelStretchRoute;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.StretchRoute;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingControllerPresenter.TrainingControllerPresenter;
import com.idnp.musicfit.presenter.trainingControllerPresenter.iTrainingControllerPresenter;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;
import com.idnp.musicfit.views.toastManager.ToastManager;


public class TrainingControllerFragment extends Fragment implements iTrainingControllerView {

    private View view;
    private iTrainingControllerPresenter trainingControllerPresenter;
    private ImageView play_button;
    private ImageView pause_button;
    private ImageView stop_button;
    private ImageView map_button;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffSet;
    private TextView lbl_play;
    private TextView lbl_pause;
    private TextView lbl_stop;
    private TextView lbl_map;
    private ImageView image_runner;

    private DBModelStretchRoute modelDBRouteStretch;
    private boolean controllAsync;

    private TextView lbl_no_resultados,lbl_resultados, number_km, lbl_km, number_pasos,lbl_pasos,number_cals,lbl_cals;
    public void executeAsync(){
        AsyncSaveStretchRoute assr=new AsyncSaveStretchRoute();
        assr.execute();
    }
    public class AsyncSaveStretchRoute extends AsyncTask<Void,Integer, Boolean>//Clase para guardar stretchRoutes de manera async
    {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected  void onPostExecute(Boolean aBoolean){
            boolean ret=true;
            if(controllAsync){
                executeAsync();
                ToastManager.toastManager.showToast("guardando tramos de rutas");
            }
        }
    }

    public TrainingControllerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view = inflater.inflate(R.layout.fragment_training_controller, container, false);
        }
        controllAsync=false;
        /*modelDBRouteStretch= new DBModelStretchRoute();
        StretchRoute stretchRoute= new StretchRoute();
        stretchRoute.setStart(new LatLng(5,5));
        stretchRoute.setEnd(new LatLng(5,5));
        stretchRoute.setId(1);
        int responseInsert=modelDBRouteStretch.insertStretchRoute(getActivity(),stretchRoute);*/

        this.trainingControllerPresenter = new TrainingControllerPresenter(this);
        Button button = (Button) view.findViewById(R.id.buttonReport);
        play_button = (ImageView)view.findViewById(R.id.play_button);
        pause_button = (ImageView)view.findViewById(R.id.pause_button);
        stop_button = (ImageView)view.findViewById(R.id.stop_button);
        map_button = (ImageView)view.findViewById(R.id.map_button);
        chronometer = (Chronometer)view.findViewById(R.id.chronometer);
        lbl_play = (TextView)view.findViewById(R.id.lbl_play);
        lbl_pause = (TextView)view.findViewById(R.id.lbl_pause);
        lbl_stop = (TextView)view.findViewById(R.id.lbl_stop);
        lbl_map = (TextView)view.findViewById(R.id.lbl_map);
        image_runner = (ImageView)view.findViewById(R.id.imageRunner);

        //results part
        lbl_no_resultados = (TextView)view.findViewById(R.id.lbl_no_resultados);
        lbl_resultados = (TextView)view.findViewById(R.id.lbl_resultados);
        lbl_km = (TextView)view.findViewById(R.id.lbl_km);
        lbl_pasos = (TextView)view.findViewById(R.id.lbl_pasos);
        lbl_cals = (TextView)view.findViewById(R.id.lbl_cals);
        number_km = (TextView)view.findViewById(R.id.number_km);
        number_pasos = (TextView)view.findViewById(R.id.number_pasos);
        number_cals = (TextView)view.findViewById(R.id.number_cals);


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time= SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int)(time/3600000);
                int m = (int)(time - h*3600000)/60000;
                int s = (int)(time - h*3600000- m*60000)/1000;
                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                chronometer.setText(t);
            }
        });
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setText("00:00:00");

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_runner.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.training_image_state_2));
                if(!running){
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
                    chronometer.start();
                    running = true;
                    controllAsync=true;
                    executeAsync();
                }
                //Toast.makeText(getActivity(),"yeah play works",Toast.LENGTH_SHORT).show();
                //just for animation
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        play_button.setVisibility(View.INVISIBLE);
                        pause_button.setVisibility(View.VISIBLE);
                        stop_button.setVisibility(View.VISIBLE);
                        map_button.setVisibility(View.VISIBLE);

                        lbl_play.setVisibility(View.INVISIBLE);
                        lbl_pause.setVisibility(View.VISIBLE);
                        lbl_stop.setVisibility(View.VISIBLE);
                        lbl_map.setVisibility(View.VISIBLE);

                        lbl_no_resultados.setVisibility(View.INVISIBLE);
                        lbl_resultados.setVisibility(View.VISIBLE);
                        lbl_pasos.setVisibility(View.VISIBLE);
                        lbl_km.setVisibility(View.VISIBLE);
                        lbl_cals.setVisibility(View.VISIBLE);
                        number_pasos.setVisibility(View.VISIBLE);
                        number_km.setVisibility(View.VISIBLE);
                        number_cals.setVisibility(View.VISIBLE);

                    }
                },100);

            }
        });
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_runner.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.training_image_state_1));
                if(running){
                    chronometer.stop();
                    pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                    controllAsync=false;

                }
                //Toast.makeText(getActivity(),"yeah pause workes",Toast.LENGTH_SHORT).show();
                //just for animation
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        play_button.setVisibility(View.VISIBLE);
                        pause_button.setVisibility(View.INVISIBLE);

                        lbl_play.setText("Continuar");

                        lbl_play.setVisibility(View.VISIBLE);
                        lbl_pause.setVisibility(View.INVISIBLE);

                    }
                },100);
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_runner.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.training_image_state_1));
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffSet = 0;
                chronometer.setText("00:00:00");
                running = false;
                controllAsync=false;//saving data;

                //  Toast.makeText(getActivity(),"yeah stop works",Toast.LENGTH_SHORT).show();
                //just for animation
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        play_button.setVisibility(View.VISIBLE);
                        pause_button.setVisibility(View.INVISIBLE);
                        stop_button.setVisibility(View.INVISIBLE);
                        map_button.setVisibility(View.INVISIBLE);

                        lbl_play.setText("Iniciar Entrenamiento");

                        lbl_play.setVisibility(View.VISIBLE);
                        lbl_pause.setVisibility(View.INVISIBLE);
                        lbl_stop.setVisibility(View.INVISIBLE);
                        lbl_map.setVisibility(View.INVISIBLE);

                        lbl_no_resultados.setVisibility(View.VISIBLE);
                        lbl_resultados.setVisibility(View.INVISIBLE);
                        lbl_pasos.setVisibility(View.INVISIBLE);
                        lbl_km.setVisibility(View.INVISIBLE);
                        lbl_cals.setVisibility(View.INVISIBLE);
                        number_pasos.setVisibility(View.INVISIBLE);
                        number_km.setVisibility(View.INVISIBLE);
                        number_cals.setVisibility(View.INVISIBLE);

                    }
                },100);
            }
        });

        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"I'm the map",Toast.LENGTH_SHORT).show();

                //just for animation
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TrainingControllerFragment.this.trainingControllerPresenter.stopTraining();
                    }
                },100);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingControllerFragment.this.trainingControllerPresenter.stopTraining();
            }
        });
        return this.view;
    }

    private void startReport(){
    }

    @Override
    public void startTraining() {

    }

    @Override
    public void pauseTraining() {

    }

    @Override
    public void stopTraining() {

    }

    @Override
    public void viewTrainingReport(Report training) {
        FragmentManager.fragmentManager.changeFragment(new TrainingReportFragment(training));
    }
}