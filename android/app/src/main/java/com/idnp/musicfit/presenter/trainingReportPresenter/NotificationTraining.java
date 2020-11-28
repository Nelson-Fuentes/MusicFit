package com.idnp.musicfit.presenter.trainingReportPresenter;
//--------------------OK CLASS----------------
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;


public class NotificationTraining extends Application {
    public static final String CHANNEL_ID_1="CHANNEL_1";
    public static final String CHANNEL_ID_2="CHANNEL_2";
    public static final String ACTION_PLAY="PLAY";
    public static final String ACTION_STOP="STOP";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel(){

            NotificationChannel notificationChannel  = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(CHANNEL_ID_1, "Channel(1)", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("channel 1 description");
                NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_ID_2, "Channel(2)", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel2.setDescription("channel 2 description");
                NotificationManager notificationManager= getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(notificationChannel);
                notificationManager.createNotificationChannel(notificationChannel2);
            }
    }
}
