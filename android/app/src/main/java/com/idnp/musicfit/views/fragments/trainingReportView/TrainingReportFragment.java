package com.idnp.musicfit.views.fragments.trainingReportView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationDataView;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationReceiver;
import com.idnp.musicfit.models.services.trainingService.TrainingService;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingReportPresenter.TrainingReportPresenter;
import com.idnp.musicfit.presenter.trainingReportPresenter.iTrainingReportPresenter;

import java.util.ArrayList;

import static com.idnp.musicfit.presenter.trainingReportPresenter.NotificationTraining.ACTION_PLAY;
import static com.idnp.musicfit.presenter.trainingReportPresenter.NotificationTraining.ACTION_STOP;
import static com.idnp.musicfit.presenter.trainingReportPresenter.NotificationTraining.CHANNEL_ID_2;


public class TrainingReportFragment extends Fragment implements iTrainingReportView, ServiceConnection {

    private View view;
    private iTrainingReportPresenter trainingReportPresenter;
    private ImageButton reportList;

    private ImageView button_play_pause,button_stop;//para el control del entrenamiento
    private TrainingNotificationDataView stateTraining;//guarda datos de la notificación sea entrenando o pause
    private boolean isTraining=false;//-----------para verificar si está activo el entrenamiento
    private TrainingService myTrainingService;//--------instancia del servicio de entrenamiento
    MediaSessionCompat mediaSession;

    private Chronometer chronometer;
    private long time_gone = 0;
    private long pauseOffSet;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final long delay_buttons = 0;

    public TrainingReportFragment(Report training) {
        this.trainingReportPresenter = new TrainingReportPresenter(this, training);
    }

    public void onCreate(Bundle savedInstanceState) {//----ok
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


        reportList = (ImageButton) this.view.findViewById(R.id.buttonlist);
        button_play_pause=(ImageView)this.view.findViewById(R.id.button_training_play_pause);
        button_stop=(ImageView)this.view.findViewById(R.id.button_training_stop);
        chronometer = (Chronometer)view.findViewById(R.id.chronometer);

        chronometer.setOnChronometerTickListener(eventChrono);
        button_play_pause.setOnClickListener(play_pause_training);
        button_stop.setOnClickListener(stop_training);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setText("00:00:00");

        this.trainingReportPresenter.loadDataNotificationTraining();//carga los datos de los estados (play training, pause training)
        mediaSession=new MediaSessionCompat(this.getContext(),"Training");

        reportList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.fragmentManager.remove(TrainingReportFragment.this);
            }
        });
        return this.view;
    }


    View.OnClickListener play_pause_training= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startTraining();
        }
    };

    View.OnClickListener stop_training= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopTraining();
        }
    };
    Chronometer.OnChronometerTickListener eventChrono=new Chronometer.OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            long time= SystemClock.elapsedRealtime() - chronometer.getBase();
            int h = (int)(time/3600000);
            int m = (int)(time - h*3600000)/60000;
            int s = (int)(time - h*3600000- m*60000)/1000;
            String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
            chronometer.setText(t);
        }
    };


    public void startTraining(){

        if(!isTraining){
            isTraining=true;//actualiza el estado
            button_play_pause.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);//cambia icono
            button_stop.setVisibility(View.VISIBLE);
            chronometer.setVisibility(View.VISIBLE);
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet - time_gone);
            chronometer.start();
            showNotification(R.drawable.ic_baseline_pause_circle_outline_24);//muestra notificación training

        }else{
            pauseTraining();
        }
    }
    public void pauseTraining(){
        isTraining=false;//actualiza estado
        button_play_pause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);//cambia ícono

        chronometer.stop();
        pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();

        showNotification(R.drawable.ic_baseline_play_circle_outline_24);//muestra notificación resting
    }
    public void stopTraining(){
        isTraining = false;
        time_gone = 0;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffSet = 0;
        chronometer.setText("00:00:00");

        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putLong("pauseOffSet", 0);
        editor.apply();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                button_play_pause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);//cambia ícono
                button_stop.setVisibility(View.INVISIBLE);
            }
        },delay_buttons);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //iniciando el fragment de map

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, new TrainingMapFragment()).commit();
    }

    @Override
    public void onStart() {
        super.onStart();

        super.onStart();
        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        chronometer.setBase(prefs.getLong("getBase",0));
        time_gone = SystemClock.elapsedRealtime() - chronometer.getBase();
        isTraining = prefs.getBoolean("running", false);
        pauseOffSet = prefs.getLong("pauseOffSet",0);

        if(isTraining){
            pauseOffSet = 0;
            startTraining();
        }else{
            time_gone = -100;
            startTraining();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pauseTraining();
                }
            },100);
        }
        time_gone = 0;
        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putLong("time_gone", 0);
        editor.apply();

    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent= new Intent(this.getActivity(),TrainingService.class);
        this.getActivity().bindService(intent,this,this.getActivity().BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putLong("time_gone", SystemClock.elapsedRealtime());
        editor.putLong("getBase",chronometer.getBase());
        editor.putLong("pauseOffSet",pauseOffSet);
        editor.putBoolean("running",isTraining);
        editor.apply();
    }

    @Override
    public void onPause() {//--cuando salimos del fragment hace unbindService y luego desconecta el servicio llamando a onServiceDisconnected
        super.onPause();
        this.getActivity().unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        TrainingService.TrainingIBinder binder= (TrainingService.TrainingIBinder)iBinder;
        myTrainingService=binder.getService();

        myTrainingService.setCallback(TrainingReportFragment.this);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        myTrainingService=null;
    }


    //---------------------interface methods overrides-----------------------------------------
    public void showNotification(int playPauseButton){

        Intent intent= new Intent(this.getContext(), MainActivity.class);
        PendingIntent contentIntent= PendingIntent.getActivity(this.getContext(),0,intent,0);

        this.trainingReportPresenter.updateDataNotificationTraining(this.isTraining);//actualizamos los datos a mostrar en la notificacion

        Intent playIntent= new Intent(this.getContext(),TrainingNotificationReceiver.class).setAction(ACTION_PLAY);
        Intent stopIntent= new Intent(this.getContext(),TrainingNotificationReceiver.class).setAction(ACTION_STOP);
        PendingIntent playPendingIntent= PendingIntent.getBroadcast(this.getContext(),0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent stopPendingIntent= PendingIntent.getBroadcast(this.getContext(),0,stopIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap picture=BitmapFactory.decodeResource(getResources(),stateTraining.getBackgroundStatus());
        Notification trainingNotification= new NotificationCompat.Builder(this.getContext(),CHANNEL_ID_2)
                .setSmallIcon(this.stateTraining.getBackgroundStatus())
                .setLargeIcon(picture)
                .setContentTitle(this.stateTraining.getStatusTitle())
                .addAction(playPauseButton,"play_pause",playPendingIntent)
                .addAction(R.drawable.ic_baseline_stop_circle_24,"stop",stopPendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(contentIntent)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager= (NotificationManager)
                this.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,trainingNotification);

    }
    public void startDataNotificationTraining(TrainingNotificationDataView stateTraining){
        this.stateTraining=stateTraining;
    }
    @Override
    public void updateDataNotificationTrainingState(String stateTrainingTitle,int backgroundTrainingState){
        this.stateTraining.setStatusTitle(stateTrainingTitle);
        this.stateTraining.setBackgroundStatus(backgroundTrainingState);
    }

}