package com.idnp.musicfit.models.services.trainingService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.idnp.musicfit.views.toastManager.ToastManager;

public class TrainingNotificationReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID_1="CHANNEL_1";
    public static final String CHANNEL_ID_2="CHANNEL_2";
    public static final String ACTION_PLAY="PLAY";
    public static final String ACTION_STOP="STOP";
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Intent newIntentService= new Intent(context,TrainingService.class);
        if(intent.getAction()!=null)
        {
            switch (intent.getAction())
            {
                case ACTION_PLAY:
                    ToastManager.toastManager.showToast("play pressed");
                    newIntentService.putExtra("myActionName",intent.getAction());//envía la acción que se está realizando(ACTION_PLAY or ACTION_STOP)
                    context.startService(newIntentService);
                    break;
                case ACTION_STOP:
                    ToastManager.toastManager.showToast("play stoped");
                    newIntentService.putExtra("myActionName",intent.getAction());//envía la acción que se está realizando(ACTION_PLAY or ACTION_STOP)
                    context.startService(newIntentService);
                    break;
            }
        }*/
    }
}
