package com.idnp.musicfit.models.services.trainingService;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Ubication;
import com.idnp.musicfit.views.toastManager.ToastManager;

import static com.idnp.musicfit.models.services.trainingService.NotificationTraining.CHANNEL_ID_1;

public class TrainingLocationIntentService extends IntentService {

    public static final String ACTION_PROCESS_UPDATE="com.idnp.musicfit" +".PROCESS_UPDATES";

    public TrainingLocationIntentService() {
        super("TrainingLocationIntentService");
    }
    protected void onHandleIntent(Intent intent) {

        startForeground(1001,getNotification());
        if(intent!=null){
            if(ACTION_PROCESS_UPDATE.equals(intent.getAction())){
                LocationResult locationResult= LocationResult.extractResult(intent);
                if (locationResult != null && locationResult.getLastLocation() != null) {
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();

                Location location=locationResult.getLastLocation();//---------TAMBIEN  se puede obtener solo una ubicación

                            //TrainingHelper.showTrainingNotification(getApplicationContext());//muestra la notificación de entrenamiento
                            TrainingHelper.saveLastLocationDB(Ubication.NONE_BREAK_POINT,getApplicationContext(),new LatLng(location.getLatitude(),location.getLongitude()));
                            ReportHelper.setKmTrainingShared(getApplicationContext(),location);
                            TrainingHelper.saveLastLocationUpdateShared(getApplicationContext(),location);//guardar en shared preferences la posición



                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastManager.toastManager.showToast("LOCATION UPDATE:(" + latitude + "," + longitude + ")");
                        }
                    });

              }
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(false);
       // ToastManager.toastManager.showToast("Destroy Service");

    }
    private Notification getNotification() {

        NotificationCompat.Builder trainingNotification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_1)
                .setSmallIcon(R.drawable.rp_icon_running)
                .setContentTitle("Training service running")
                .setContentText("Training service is running in background")
                .setAutoCancel(true);
        return trainingNotification.build();
    }
}