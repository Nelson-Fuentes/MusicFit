package com.idnp.musicfit.models.services.authenticationService;

import android.content.SharedPreferences;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitException;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitResponse;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitService;
import com.idnp.musicfit.models.services.musicfitPreferences.MusicfitPreferencesService;

import org.json.JSONObject;
import java.net.HttpURLConnection;

import javax.xml.transform.sax.TemplatesHandler;

public class AuthenticationService {

    public static AuthenticationService authenticationService;

    private static final String USERNAME_LABEL = "username";
    private static final String PASSWORD_LABEL = "password";
    private static final String PREFERENCES_FILE = "authentication";
    private static final String PREFERENCES_TOKEN_KEY = "auth_token";
    private static final String INCOGNITE_AUTH_TOKEN = "incognite_auth__token";
    private static final String GOOGLE_AUTH_TOKEN = "google_auth_token";
    private static final String FACEBOOK_AUTH_TOKEN = "facebook_auth_token";



    private static User currentUser;

    public  String auth(String username, String password) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(USERNAME_LABEL, username);
        jsonObject.put(PASSWORD_LABEL, password);
        MusicFitResponse response = MusicFitService.musicfitService.post(MusicFitService.AUTHENTICATION_PATH, jsonObject);
        response.throwException();
        if (response.getRequestCode()==HttpURLConnection.HTTP_OK) {
            String token = (new JSONObject(response.getBody())).get("token").toString();
            this.writeToken(token);
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
        SharedPreferences preferences = MusicfitPreferencesService.musicfitPreferencesService.openSharedPreferencesFile(PREFERENCES_FILE);
        String token = MusicfitPreferencesService.musicfitPreferencesService.readPreference(preferences, PREFERENCES_TOKEN_KEY);
        return token !=null;
    }

    private void writeToken(String token){
        SharedPreferences preferences = MusicfitPreferencesService.musicfitPreferencesService.openSharedPreferencesFile(PREFERENCES_FILE);
        MusicfitPreferencesService.musicfitPreferencesService.writePreference(preferences, PREFERENCES_TOKEN_KEY, token);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean authenticationIncognite(){
        this.writeToken(INCOGNITE_AUTH_TOKEN);
        return true;
    }
    public boolean authenticationFacebook(){
        this.writeToken(FACEBOOK_AUTH_TOKEN);
        return true;
    }

    public boolean authenticationGoogle(){
        this.writeToken(GOOGLE_AUTH_TOKEN);
        return true;
    }

    public void logout(){
        SharedPreferences preferences = MusicfitPreferencesService.musicfitPreferencesService.openSharedPreferencesFile(PREFERENCES_FILE);
        MusicfitPreferencesService.musicfitPreferencesService.removePreference(preferences, PREFERENCES_TOKEN_KEY);
    }
}
