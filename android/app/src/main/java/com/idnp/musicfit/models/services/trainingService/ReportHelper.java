package com.idnp.musicfit.models.services.trainingService;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;
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


    //---------------------------------------------------------------------------------------------
    //-------------------------             KM          -------------------------------------------
    //---------------------------------------------------------------------------------------------
    public static void setKmTrainingShared(Context context,Location location){
        String [] lastLocation=TrainingHelper.getSavedLastLocationUpdateShared(context).split("/");
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
//        +MusicfitAuthenticationManagerService.authenticationService.getCurrentUserId();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(
                        START_ID_TRAINING_SHARED,
                        TrainingHelper.getDateAndTime())
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

    public static void startTrainingVarsShared(Context context){
        ReportHelper.setStartIdTrainingShared(context);
        TrainingHelper.setCountPositionsTrainingReport(context,TrainingHelper.INIT_COUNT_POSITION);
        TrainingHelper.setLocationRequestStatus(context,TrainingHelper.TRAINING);
        ReportHelper.resetKmTrainingShared(context);
        ReportHelper.setDurationTrainingShared(context);
        ToastManager.toastManager.showToast("agregando el inicio");
    }

    public static void pauseTrainingVarsShared(Context context){

        String []location=TrainingHelper.getSavedLastLocationUpdateShared(context).split("/");
        double lat=Double.parseDouble(location[0]);
        double lng=Double.parseDouble(location[1]);
        TrainingHelper.saveLastLocationDB(Ubication.BREAK_POINT,context,new LatLng(lat,lng));
        TrainingHelper.setLocationRequestStatus(context,TrainingHelper.NO_TRAINING);
    }
    public static  void stopTrainingVarsShared(Context context,String location){
        ReportHelper.resetStartIdTrainingShared(context);
        TrainingHelper.setLocationRequestStatus(context,TrainingHelper.NO_TRAINING);
        ReportHelper.resetDurationTrainingShared(context);
        TrainingHelper.setCountPositionsTrainingReport(context,TrainingHelper.INIT_COUNT_POSITION);
        ReportHelper.resetKmTrainingShared(context);
        ReportHelper.setFinalPositionTrainingShared(context,location);

    }

    public static void initReferences(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(ReportHelper.START_ID_TRAINING_SHARED,ReportHelper.NONE_START_ID)
                .apply();
    }


}
