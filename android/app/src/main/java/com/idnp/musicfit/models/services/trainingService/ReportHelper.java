package com.idnp.musicfit.models.services.trainingService;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Ubication;
import com.idnp.musicfit.models.services.authenticationService.MusicfitAuthenticationManagerService;
import com.idnp.musicfit.views.toastManager.ToastManager;

public class ReportHelper {

    //-------------------------KM TRAINING-------------------

    public static final String KEY_KM_TRAINING_SHARED="key-km-training";
    public static final float NONE_KM_VALUE =0;

    //------------------------ START ID TRAINING----------------------
    public static final String START_ID_TRAINING_SHARED ="start-time-userid-reportid-training-shared";
    public static final String NONE_START_ID="none-start-time-uiserid-reportid";

    //------------------------ DURATION---------------------
    public static final String KEY_DURATION_SHARED =" duration - key - shared";
    public static final String NONE_DURATION="none - duration";

    //--------------------START POSITION -------------------
    public static final String KEY_START_POSITION_TRAINING_SHARED="key-start-position-shared";
    public static final String NONE_START_POSITION_TRAINING="none-start-position";

    //--------------------FINAL POSITION -------------------
    public static final String KEY_FINAL_POSITION_TRAINING_SHARED="key-final-position-shared";
    public static final String NONE_FINAL_POSITION_TRAINING="none-final-position";

    public static final String NONE_USER_ID="nouserid";

    //---------------------------------------------------------------------------------------------
    //-------------------------             KM          -------------------------------------------
    //---------------------------------------------------------------------------------------------
    public static void setKmTrainingShared(Context context,Location location){
        boolean training=TrainingHelper.getLocationRequestStatus(context);
        if(training){
            String lastPosition=TrainingHelper.getSavedLastLocationUpdateShared(context);
            if(!lastPosition.equals(TrainingHelper.NONE_LAST_LOCATION)){
                String[] lastLocation=lastPosition.split("/");
                float lastKm= ReportHelper.getKmTrainingShared(context);
                PreferenceManager.getDefaultSharedPreferences(context)
                        .edit()
                        .putFloat(
                                KEY_KM_TRAINING_SHARED,
                                lastKm+ReportHelper.getDistanceKM(
                                        Double.parseDouble(lastLocation[0]),
                                        Double.parseDouble(lastLocation[1]),
                                        location.getLatitude(),
                                        location.getLongitude()
                                )
                        )
                        .apply();
            }
        }
    }

    public static float getKmTrainingShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getFloat(KEY_KM_TRAINING_SHARED,NONE_KM_VALUE);
    }
    public static void resetKmTrainingShared(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putFloat(
                        KEY_KM_TRAINING_SHARED,
                        NONE_KM_VALUE)
                .apply();
    }

    public static float getDistanceKM(double start_lat,double start_lng,double end_lat,double end_lng){
        float [] result=new float[10];
        Location.distanceBetween(
                start_lat,
                start_lng,
                end_lat,
                end_lng,
                result
        );
        return result[0]/1000;
    }
    //---------------------------------------------------------------------------------------------
    //-------------------------    ****     CAL  ***      -------------------------------------------
    //---------------------------------------------------------------------------------------------
    public static void setCalTrainingShared(Context context,Location location){
        String [] lastLocation=TrainingHelper.getSavedLastLocationUpdateShared(context).split("/");
        float lastKm= ReportHelper.getKmTrainingShared(context);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putFloat(
                        KEY_KM_TRAINING_SHARED,
                        lastKm+ReportHelper.getCalBurned(
                                Double.parseDouble(lastLocation[0]),
                                Double.parseDouble(lastLocation[1]),
                                location.getLatitude(),
                                location.getLongitude()
                        )
                )
                .apply();
    }
    public static float getCalTrainingShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getFloat(KEY_KM_TRAINING_SHARED,NONE_KM_VALUE);
    }


    public static float getCalBurned(double start_lat,double start_lng,double end_lat,double end_lng){
        float [] result=new float[10];
        Location.distanceBetween(
                start_lat,
                start_lng,
                end_lat,
                end_lng,
                result
        );
        return result[0]/1000;
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------     TIME ID USER      -------------------------------------------
    //---------------------------------------------------------------------------------------------

    public static void setStartIdTrainingShared(Context context){
       String userid=ReportHelper.NONE_USER_ID;
       String user=MusicfitAuthenticationManagerService.authenticationService.getCurrentUserId();
       if(user!=null){
           userid=user;
       }
       PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        START_ID_TRAINING_SHARED,
                        TrainingHelper.getDateAndTime()+"/"+userid)
                .apply();
    }

    public static void resetStartIdTrainingShared(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        START_ID_TRAINING_SHARED,
                        NONE_START_ID)
                .apply();
    }

    public static String getStartIdTrainingShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(START_ID_TRAINING_SHARED,NONE_START_ID);
    }
    //---------------------------------------------------------------------------------------------
    //---------------------------     DURATION      -------------------------------------------
    //---------------------------------------------------------------------------------------------

    public static void setDurationTrainingShared(Context context){
//        +MusicfitAuthenticationManagerService.authenticationService.getCurrentUserId();
      /*  PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        KEY_DURATION_SHARED,
                        TrainingHelper.getDateAndTime())
                .apply();*/
    }

    public static void resetDurationTrainingShared(Context context){
        /*PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        KEY_DURATION_SHARED,
                        NONE_DURATION)
                .apply();*/
    }

    public static String getDurationTrainingShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_DURATION_SHARED,NONE_DURATION);
    }
    //---------------------------------------------------------------------------------------------
    //---------------------------     START POSITION      -------------------------------------------
    //---------------------------------------------------------------------------------------------

    public static void setStartPositionTrainingShared(Context context,String location){

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        KEY_START_POSITION_TRAINING_SHARED,
                        location)
                .apply();
    }

    public static void resetStartPositionTrainingShared(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        KEY_START_POSITION_TRAINING_SHARED,
                        NONE_START_POSITION_TRAINING)
                .apply();
    }

    public static String getStartPositionTrainingShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_START_POSITION_TRAINING_SHARED,NONE_START_POSITION_TRAINING);
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------     FINAL POSITION      -------------------------------------------
    //---------------------------------------------------------------------------------------------

    public static void setFinalPositionTrainingShared(Context context,String location){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        KEY_FINAL_POSITION_TRAINING_SHARED,
                        location)
                .apply();
    }

    public static void resetFinalPositionTrainingShared(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        KEY_FINAL_POSITION_TRAINING_SHARED,
                        NONE_FINAL_POSITION_TRAINING)
                .apply();
    }

    public static String getFinalPositionTrainingShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_FINAL_POSITION_TRAINING_SHARED,NONE_FINAL_POSITION_TRAINING);
    }


    //---------------------------------------------------------------------------------------------
    //----------------------     START PAUSE RESUME TRAINING      ---------------------------------
    //---------------------------------------------------------------------------------------------

    public static void startTrainingVarsShared(Context context,String location){
        ReportHelper.setStartIdTrainingShared(context);
        ReportHelper.setStartPositionTrainingShared(context,location);
        TrainingHelper.setCountPositionsTrainingReport(context,TrainingHelper.INIT_COUNT_POSITION);
        TrainingHelper.setLocationRequestStatus(context,TrainingHelper.TRAINING);
        ReportHelper.resetKmTrainingShared(context);
        ReportHelper.setDurationTrainingShared(context);
    }

    public static void pauseTrainingVarsShared(Context context){
        String position=TrainingHelper.getSavedLastLocationUpdateShared(context);
        if(!position.equals(TrainingHelper.NONE_LAST_LOCATION)){
            String []location=position.split("/");
            double lat=Double.parseDouble(location[0]);
            double lng=Double.parseDouble(location[1]);
            TrainingHelper.saveLastLocationDB(Ubication.BREAK_POINT,context,new LatLng(lat,lng));
            TrainingHelper.setLocationRequestStatus(context,TrainingHelper.NO_TRAINING);
        }
    }
    public static  void stopTrainingVarsShared(Context context,String location){
        ReportHelper.setFinalPositionTrainingShared(context,location);
        ReportHelper.resetStartIdTrainingShared(context);
        TrainingHelper.setLocationRequestStatus(context,TrainingHelper.NO_TRAINING);
        ReportHelper.resetDurationTrainingShared(context);
        TrainingHelper.setCountPositionsTrainingReport(context,TrainingHelper.INIT_COUNT_POSITION);
        ReportHelper.resetKmTrainingShared(context);
    }

    public static void initReferences(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(ReportHelper.START_ID_TRAINING_SHARED,ReportHelper.NONE_START_ID)
                .apply();
    }
    public static String refactorId(String id){
        String[] ID=id.split("/");
        return ID[0]+ID[1]+ID[2]+ID[3]+ID[4]+ID[5]+ID[6];
    }

    public static boolean loadReportToSendFirebase(Context context,Location location){
        String id=ReportHelper.getStartIdTrainingShared(context);

        String []idArray=id.split("/");
        int year=Integer.parseInt(idArray[0]);
        int month=Integer.parseInt(idArray[1]);
        int day=Integer.parseInt(idArray[2]);
        int hour=Integer.parseInt(idArray[3]);
        int min=Integer.parseInt(idArray[4]);
        int sec=Integer.parseInt(idArray[5]);
        String idUser=idArray[6];
        if(idUser.equals(ReportHelper.NONE_USER_ID)) return  false;

        String [] startPosition=ReportHelper.getStartPositionTrainingShared(context).split("/");
        double lat=Double.parseDouble(startPosition[0]);
        double lon=Double.parseDouble(startPosition[1]);
        com.idnp.musicfit.models.entities.LatLng startLocation=new com.idnp.musicfit.models.entities.LatLng(lat,lon);

        Report nuevo= new Report(day,month,year,hour,min,sec,startLocation);
        nuevo.setEndP(new com.idnp.musicfit.models.entities.LatLng(location.getLatitude(),location.getLongitude()));
        String reportIdString=ReportHelper.refactorId(id);
        nuevo.setID(reportIdString);
        nuevo.setKM(1);
        nuevo.setKcal(1);
        nuevo.setEnd(1);
        nuevo.setDurationHour(12);
        nuevo.setDurationMin(15);
        nuevo.setDurationSec(12);

        FireBaseReportHelper base=new FireBaseReportHelper();
        base.saveReportTraining(
                idUser,
                nuevo
                );
        base.saveLocationsTraining(reportIdString,TrainingHelper.getLocationsReport(context,reportIdString));
        return true;
    }

}
