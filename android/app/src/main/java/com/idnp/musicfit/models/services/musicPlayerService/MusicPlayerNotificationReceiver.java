package com.idnp.musicfit.models.services.musicPlayerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.idnp.musicfit.views.toastManager.ToastManager;

public class MusicPlayerNotificationReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID_1="CHANNEL_1";
    public static final String CHANNEL_ID_2="CHANNEL_2";
    public static final String ACTION_PLAY="PLAY";
    public static final String ACTION_NEXT="NEXT";
    public static final String ACTION_PREV="PREV";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntentService= new Intent(context, MusicPlayerService.class);
        if(intent.getAction()!=null)
        {
            switch (intent.getAction())
            {
                case ACTION_PLAY:
                    ToastManager.toastManager.showToast("Play");
                    newIntentService.putExtra("myActionName",intent.getAction());//envía la acción que se está realizando(ACTION_PLAY or ACTION_STOP)
                    context.startService(newIntentService);
                    break;
                case ACTION_NEXT:
                    ToastManager.toastManager.showToast("Siguiente Cancion");
                    newIntentService.putExtra("myActionName",intent.getAction());//envía la acción que se está realizando(ACTION_PLAY or ACTION_STOP)
                    context.startService(newIntentService);
                    break;
                case ACTION_PREV:
                    ToastManager.toastManager.showToast("Cancion Anterior");
                    newIntentService.putExtra("myActionName",intent.getAction());//envía la acción que se está realizando(ACTION_PLAY or ACTION_STOP)
                    context.startService(newIntentService);
                    break;
            }
        }
    }
}
