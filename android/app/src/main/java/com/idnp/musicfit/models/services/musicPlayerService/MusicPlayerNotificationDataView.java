package com.idnp.musicfit.models.services.musicPlayerService;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationManagerCompat;

import com.idnp.musicfit.models.entities.Song;

public class MusicPlayerNotificationDataView {
    public static final String STATE_PLAY="PLAY";
    public static final String STATE_PAUSE="PAUSE";
    private String statusTitle;
    private int backgroundStatus;

    public MusicPlayerNotificationDataView(String statusTitle, int backgroundStatus) {
        this.statusTitle = statusTitle;
        this.backgroundStatus = backgroundStatus;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public int getBackgroundStatus() {
        return backgroundStatus;
    }

    public void setBackgroundStatus(int backgroundStatus) {
        this.backgroundStatus = backgroundStatus;
    }
}
