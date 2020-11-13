package com.idnp.musicfit.models.services.authenticationService;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitException;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitResponse;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitService;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

public class AuthenticationService {

    private static boolean isLogged;

    public static AuthenticationService authenticationService;

    private static final String USERNAME_LABEL = "username";
    private static final String PASSWORD_LABEL = "password";
    private static User currentUser;

    public  String auth(String username, String password) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(USERNAME_LABEL, username);
        jsonObject.put(PASSWORD_LABEL, password);
        MusicFitResponse response = MusicFitService.musicfitService.post(MusicFitService.AUTHENTICATION_PATH, jsonObject);
        response.throwException();
        if (response.getRequestCode()==HttpURLConnection.HTTP_OK) {
            isLogged = true;
            String token = (new JSONObject(response.getBody())).get("token").toString();
            return token;
        } else if (response.getRequestCode()==HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new MusicFitException(R.string.auth_invalid);
        } else if (response.getRequestCode() == HttpURLConnection.HTTP_SERVER_ERROR) {
            throw new MusicFitException(R.string.server_error);
        } else {
            throw new MusicFitException(response.getErrorBody());
        }
    }

    public boolean isLogged(){
        return isLogged;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean authenticationIncognite(){
        isLogged =true;
        return true;
    }
    public boolean authenticationFacebook(){
        isLogged =true;
        return true;
    }

    public boolean authenticationGoogle(){
        isLogged =true;
        return true;
    }
}
