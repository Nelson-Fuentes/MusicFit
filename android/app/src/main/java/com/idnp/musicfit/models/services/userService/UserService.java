package com.idnp.musicfit.models.services.userService;

import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitException;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitResponse;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitService;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicfitConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class UserService {

    public static UserService userService;
    private static final String USERNAME_LABEL = "username";
    private static final String PASSWORD_LABEL = "password";
    private static final String FIRST_NAME_LABEL = "first_name";
    private static final String LAST_NAME_LABEL = "last_name";
    private static final String EMAIL_LABEL = "email";

    public User registerUser(String username,  String firstname, String lastname, String email, String password) throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(USERNAME_LABEL, username);
        jsonObject.put(PASSWORD_LABEL, password);
        jsonObject.put(LAST_NAME_LABEL, lastname);
        jsonObject.put(FIRST_NAME_LABEL, firstname);
        jsonObject.put(EMAIL_LABEL, email);
        MusicFitResponse response = MusicFitService.musicfitService.post(MusicFitService.USER_REGISTRATION, jsonObject);
        response.throwException();
        if (response.getRequestCode()!= HttpURLConnection.HTTP_NO_CONTENT){
            String error_body = "";
            JSONObject jsonError = new JSONObject(response.getBody());
            for (Iterator<String> it = jsonError.keys(); it.hasNext(); ) {
                String key = it.next();
                throw  new MusicFitException(key.toUpperCase() + ": " + (new JSONArray(jsonError.getString(key)).get(0)));
            }
        }
        return new User(username, firstname, lastname, email);
    }
}
