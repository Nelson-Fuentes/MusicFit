package com.idnp.musicfit.models.services.trainingService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

//import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.LatLng;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.entities.Ubication;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;
import com.idnp.musicfit.views.fragments.trainingReportView.iTrainingReportView;
import com.idnp.musicfit.views.toastManager.ToastManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrainingHelper {

    public static final String KEY_LAST_LOCATION_SHARED = "key-location-training";
    public static final String KEY_IS_TRAINING_SHARED="key - shared - request";
    public static final String KEY_COUNT_POSITION_TRAINING_SHARED="count - position - training";


    public static final boolean NO_TRAINING=false;
    public static final boolean TRAINING=true;
    public static final String NONE_LAST_LOCATION="no-position";
    public static final int INIT_COUNT_POSITION=0;



    private static String getLocationResultText(Location mLocation){//Un string con la última ubicación detectada
        if(mLocation==null){
            return  NONE_LAST_LOCATION;
        }else{
            StringBuilder sb= new StringBuilder();
                sb.append(mLocation.getLatitude());
                sb.append("/");
                sb.append(mLocation.getLongitude());
            return sb.toString();
        }
    }

    private static CharSequence getLocationResultTitle(){
        String result="Location ";
        return result+":"+ DateFormat.getDateInstance().format(new Date());
    }
    public static void  showTrainingNotification(Context mContext){
        Intent notificationIntent = new Intent(mContext, TrainingReportFragment.class);
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder noficationBuilder=null;
        noficationBuilder= new NotificationCompat.Builder(mContext,NotificationTraining.CHANNEL_ID_1)
                .setContentTitle(getLocationResultTitle())
                .setContentText("You training starts")
                .setSmallIcon(R.drawable.rp_icon_running)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        getNotificationManager(mContext).notify(0,noficationBuilder.build());
    }
    private static NotificationManager getNotificationManager(Context mContext){
        NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public static void saveLastLocationDB(int breakPointStatus, Context mContext, LatLng mLocation){
        if(TrainingHelper.getLocationRequestStatus(mContext)){
            String idTrainingReport=ReportHelper.getStartIdTrainingShared(mContext);
            if(!idTrainingReport.equals(ReportHelper.NONE_START_ID)){
                String id=ReportHelper.refactorId(idTrainingReport);
                Log.d("example","saving data with id: "+id);
                int countPosition=TrainingHelper.getCountPositionsTrainingReport(mContext);
                DBManager dbManager = new DBManager(mContext);
                dbManager.open();
                dbManager.insertUbication(new Ubication(
                        id,
                        countPosition,
                        mLocation,
                        breakPointStatus));
                TrainingHelper.setCountPositionsTrainingReport(mContext,countPosition+1);
                dbManager.close();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------
    //--------------------               IS TRAINING                  ------------------------------
    //-----------------------------------------------------------------------------------------------

    public static boolean getLocationRequestStatus(Context context){
       return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_IS_TRAINING_SHARED, TrainingHelper.NO_TRAINING);
    }
    public static  void setLocationRequestStatus(Context context, boolean isTraining){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_IS_TRAINING_SHARED,isTraining)
                .apply();
    }

    //-----------------------------------------------------------------------------------------------
    //--------------------       COUNT LOCATIONS TO SAVE IN ORDER        ------------------------------
    //-----------------------------------------------------------------------------------------------
    public static int getCountPositionsTrainingReport(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(KEY_COUNT_POSITION_TRAINING_SHARED,INIT_COUNT_POSITION);
    }
    public static void setCountPositionsTrainingReport(Context context, int count){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(KEY_COUNT_POSITION_TRAINING_SHARED,count)
                .apply();
    }
    //-----------------------------------------------------------------------------------------------
    //--------------------          SAVE LAST LOCATION UPDATE          ------------------------------
    //-----------------------------------------------------------------------------------------------

    public static void saveLastLocationUpdateShared(Context mContext,Location mLocation){//----------------------------------------------------------ok

        if(ReportHelper.getStartPositionTrainingShared(mContext).equals(ReportHelper.NONE_START_POSITION_TRAINING))
        {

            ReportHelper.setStartPositionTrainingShared(mContext, ""+mLocation.getLatitude()+"/"+mLocation.getLongitude());
        }
        if(TrainingHelper.getLocationRequestStatus(mContext)){
            PreferenceManager.getDefaultSharedPreferences(mContext)
                    .edit()
                    .putString(KEY_LAST_LOCATION_SHARED,getLocationResultText(mLocation))
                    .apply();
        }
    }
    public static String getSavedLastLocationUpdateShared(Context context){//------------------------------ok
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LAST_LOCATION_SHARED,NONE_LAST_LOCATION);
    }


    public static String getDateAndTime(){
        Calendar cal= Calendar.getInstance();
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH)+1;
        int year=cal.get(Calendar.YEAR);
       /* Date time=cal.getTime();
        int sec= (int)Math.ceil(Math.random());
        int min= (int)Math.ceil(Math.random());
        int hour= (int)Math.ceil(Math.random());*/

        String formated="";
        SimpleDateFormat dateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("HH/mm/ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
            Date today = Calendar.getInstance().getTime();
            formated= dateFormat.format(today);
        }
        return ""+year+"/"+month+"/"+day+"/"+formated;
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
    //-----------getters----------------------------------
    public static ArrayList<Ubication> getLocationsReport(Context context, String idReport){
        String id=ReportHelper.refactorId(idReport);
        Log.d("example","getting data with id: "+id);
        DBManager dbManager= new DBManager(context);
        dbManager.open();
        ArrayList<Ubication> ret=dbManager.getUbications(id);
        Log.d("example","getting data with size: "+ret.size());
        dbManager.close();
        return ret;
    }
    public static ArrayList<Report> getReports(Context context){
        DBManager dbManager= new DBManager(context);
        dbManager.open();
        ArrayList<Report> ret=dbManager.getReports();
        dbManager.close();
        return ret;
    }
}
