package com.idnp.musicfit.models.services.trainingService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingHelper {

    public static final String KEY_LOCATION_SHARED_RESULT = "key-location-training";
    public static final String IS_TRAINING_SHARED_KEY="key - shared - request";
    public static final String KEY_TRAINING_ID_SHARED="key - shared - training- id";
    public static final String NONE_TRAINING_VALUE="none";
    public static final String NONE_POSITION="no-position";

    private Context mContext;
    private Location mLocation;

    public TrainingHelper(Context context,Location location){
        this.mContext=context;
        this.mLocation=location;
    }

    public String getLocationResultText(){//Un string con todas las ubicaciones detectadas concatenadas
        if(mLocation==null){
            return  NONE_POSITION;
        }else{
            StringBuilder sb= new StringBuilder();
                sb.append(mLocation.getLatitude());
                sb.append("/");
                sb.append(mLocation.getLongitude());
            return sb.toString();
        }
    }
    private CharSequence getLocationResultTitle(){
        String result="Location ";
        return result+":"+ DateFormat.getDateInstance().format(new Date());
    }
    public void showTrainingNotification(){
        Intent notificationIntent = new Intent(mContext, TrainingReportFragment.class);
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder noficationBuilder=null;
        noficationBuilder= new NotificationCompat.Builder(mContext,NotificationTraining.CHANNEL_ID_1)
                .setContentTitle(getLocationResultTitle())
                .setContentText(getLocationResultText())
                .setSmallIcon(R.drawable.rp_icon_running)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        getNotificationManager().notify(0,noficationBuilder.build());
    }
    private  NotificationManager getNotificationManager(){
        NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }
    public void saveTrainingPositionDB(){
        LatLng latLng= new LatLng(mLocation.getLatitude(),mLocation.getLongitude());//cambiar por ubicación
        if(TrainingHelper.getLocationRequestStatus(mContext)){
            if(!TrainingHelper.getTrainingStartedRequestId(mContext).equals(NONE_TRAINING_VALUE)){
                //se puede guardar en sqlite
            }
        }
    }
    public static void saveStartTrainingDB(Report report){//---------- guarda en la BD el objeto Report

    }
    public static void saveEndTrainingDB(Report report){//---------- guarda en la BD el objeto Report

    }

    public static boolean getLocationRequestStatus(Context context){
       return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(IS_TRAINING_SHARED_KEY,false);
    }
    public static  void setLocationRequestStatus(Context context, boolean isTraining){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(IS_TRAINING_SHARED_KEY,isTraining)
                .apply();
    }
    public static String getTrainingStartedRequestId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_TRAINING_ID_SHARED,NONE_TRAINING_VALUE);
    }
    public static  void setTrainingStartedRequestId(Context context, String id){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_TRAINING_ID_SHARED,id)
                .apply();
    }
    public void saveLastLocationUpdate(){
        if(TrainingHelper.getLocationRequestStatus(mContext)){
            PreferenceManager.getDefaultSharedPreferences(mContext)
                    .edit()
                    .putString(KEY_LOCATION_SHARED_RESULT,getLocationResultText())
                    .apply();
        }


    }
    public static String getSavedLastLocationUpdate(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LOCATION_SHARED_RESULT,NONE_POSITION);
    }
    public static ArrayList<Report> getTrainingList(){
        //obtener la lista de entrenamientos desde la base de datos en nube

        ArrayList<Report> trainings = new ArrayList<Report>();

        Report nuevo = new Report(19,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo1 = new Report(12,10,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo1.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo2 = new Report(9,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo2.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo3 = new Report(15,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo3.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo4 = new Report(23,10,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo4.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo5 = new Report(15,11,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo5.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo6 = new Report(23,9,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo6.setEndP(new LatLng(-16.4356583,-71.5651415));
        Report nuevo7 = new Report(30,2,2020,11,17,52,new LatLng(-16.4322583,-71.5642415));
        nuevo7.setEndP(new LatLng(-16.4356583,-71.5651415));

        trainings.add(nuevo);
        trainings.add(nuevo1);
        trainings.add(nuevo2);
        trainings.add(nuevo3);
        trainings.add(nuevo4);
        trainings.add(nuevo5);
        trainings.add(nuevo6);
        trainings.add(nuevo7);

        return  trainings;
    }

}
