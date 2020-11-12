package com.idnp.musicfit.models.services.musicFitRemoteService;

import android.os.AsyncTask;

import com.idnp.musicfit.models.entities.User;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MusicFitService {
    /*
    Esta clase no se como se implemnteria asi que esta sujeta a cambios
     */
    private static final String MUSICTFIT_API_URI = "http://musicfit2020.herokuapp.com/api/v1/";

    public static MusicFitService musicfitService;
    public static final String USER_REGISTRATION_PATH = "user/create";
    public static final String AUTHENTICATION_PATH = "auth/";

    public MusicFitResponse post(String path, JSONObject jsonObject) throws ExecutionException, InterruptedException {
        MusicfitConnection connection = new MusicfitConnection();
        connection.setData(jsonObject);
        return connection.execute(MUSICTFIT_API_URI+path, MusicfitConnection.METHOD_POST).get();

    }
}
