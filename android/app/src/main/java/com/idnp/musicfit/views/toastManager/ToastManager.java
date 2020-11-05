package com.idnp.musicfit.views.toastManager;

import android.content.Context;
import android.widget.Toast;

public  class ToastManager{

    public static ToastManager toastManager;

    private Context context;

    public ToastManager(Context context){
        this.context  = context;
    }

    public Toast showToast(String text){
        Toast toast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
        toast.show();
        return  toast;
    }

    public Toast showToast(int text){
        Toast toast = Toast.makeText(context,this.context.getString(text), Toast.LENGTH_SHORT);
        toast.show();
        return  toast;
    }
}