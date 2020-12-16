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

import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;

import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.entities.Ubication;
import com.idnp.musicfit.models.services.authenticationService.MusicfitAuthenticationManagerService;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.trainingReportListView.iTrainingReportListView;
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
                        new com.idnp.musicfit.models.entities.LatLng(mLocation.latitude,mLocation.longitude),
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
            PreferenceManager.getDefaultSharedPreferences(mContext)
                    .edit()
                    .putString(KEY_LAST_LOCATION_SHARED,getLocationResultText(mLocation))
                    .apply();
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
    //-----------getters----------------------------------
    public static ArrayList<Ubication> getLocationsReport(Context context, String idReport){

        Log.d("example","getting data with id: "+idReport);
        DBManager dbManager= new DBManager(context);
        dbManager.open();
        ArrayList<Ubication> ret=dbManager.getUbications(idReport);
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
