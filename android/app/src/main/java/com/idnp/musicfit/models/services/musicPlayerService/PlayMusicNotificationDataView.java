package com.idnp.musicfit.models.services.musicPlayerService;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationManagerCompat;

import com.idnp.musicfit.models.entities.Song;

public class PlayMusicNotificationDataView  {
    public static final String CHANNEL_ID = "channell";

    public static final String ACTIONPREVIUS = "actionprevious";
    public static final String CHANNEL_PLAY = "actionplay";
    public static final String CHANNEL_NEXT = "actionnext";

    public static Notification notification;

    public static void createNotification(Context context, Song song, int playbutton, int pos, int size){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat=new MediaSessionCompat(context, "TAG");
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),song.getMusic());

        }
    }
}
