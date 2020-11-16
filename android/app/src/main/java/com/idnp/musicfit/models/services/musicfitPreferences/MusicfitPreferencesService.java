package com.idnp.musicfit.models.services.musicfitPreferences;

import android.content.Context;
import android.content.SharedPreferences;


public class MusicfitPreferencesService {

    public static MusicfitPreferencesService musicfitPreferencesService;

    private Context context;

    public  MusicfitPreferencesService(Context context){
        this.context = context;
    }

    public SharedPreferences openSharedPreferencesFile(String file){
        return this.context.getSharedPreferences(file, Context.MODE_PRIVATE);
    }

    public String readPreference (SharedPreferences preferences, String key){
        return preferences.getString(key, null);
    }

    public void writePreference(SharedPreferences preferences, String key, String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void removePreference(SharedPreferences preferences, String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
