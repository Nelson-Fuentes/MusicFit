package com.idnp.musicfit.models.services.authenticationService;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitException;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitResponse;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitService;
import com.idnp.musicfit.models.services.musicfitFirebase.MusicfitFireBase;
import com.idnp.musicfit.models.services.musicfitPreferences.MusicfitPreferencesService;
import com.idnp.musicfit.presenter.loginPresenter.iLoginPresenter;
import com.idnp.musicfit.views.toastManager.ToastManager;

import org.json.JSONObject;
import java.net.HttpURLConnection;

public class MusicfitAuthenticationManagerService {

    public static MusicfitAuthenticationManagerService authenticationService;

    private static final String USERNAME_LABEL = "username";
    private static final String PASSWORD_LABEL = "password";
    public static final String PREFERENCES_FILE = "authentication";
    public static final String PREFERENCES_TOKEN_KEY = "auth_token_firebase";
    private static final String INCOGNITE_AUTH_TOKEN = "incognite_auth__token";
    private static final String GOOGLE_AUTH_TOKEN = "google_auth_token";
    private static final String FACEBOOK_AUTH_TOKEN = "facebook_auth_token";
    public static final String ACCOUNT_TYPE = "com.idnp.musicfit";
    public static final String AUTH_TOKEN_TYPE_KEY = "TOKEN_TYPE";
    private static final String INCOGNITE_AUTH_USERNAME = "Usuario incognito";
    public static final int GOOGLE_AUTH_RESULT = 777;


    private Account account;
    private AccountManager accountManager;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth auth;

    public MusicfitAuthenticationManagerService(Context context){
        this.auth = new MusicfitFireBase().getAuth();
        this.accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length>0){
            this.account = accounts[0];
        }
    }

    public  void auth(String email, String password, iLoginPresenter loginPresenter) throws Exception {
        Task<AuthResult> task = this.auth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String token =task.getResult().getUser().getUid();
                    writeToken(token);
                    addAccountToManager(task.getResult().getUser().getEmail(), token);
                    loginPresenter.handleSignInSucess();
                } else {
                    ToastManager.toastManager.showToast(task.getException().getMessage());
                }
            }
        });
    }

    public boolean isLogged(){
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length>0){
            this.account = accounts[0];
        } else {
            this.account = null;
        }
        return this.account !=null;
    }

    private void addAccountToManager(String username, String token){
        this.account = new Account(username, ACCOUNT_TYPE);
        accountManager.addAccountExplicitly(this.account, token, null);
    }


    public boolean isIncognite(){
        String accoun_token = this.accountManager.getPassword(this.account);
        return INCOGNITE_AUTH_TOKEN.equals(accoun_token);
    }

    private void writeToken(String token){
        SharedPreferences preferences = MusicfitPreferencesService.musicfitPreferencesService.openSharedPreferencesFile(PREFERENCES_FILE);
        MusicfitPreferencesService.musicfitPreferencesService.writePreference(preferences, PREFERENCES_TOKEN_KEY, token);
    }


    public String authenticationIncognite(){
        this.writeToken(INCOGNITE_AUTH_TOKEN);
        this.addAccountToManager(INCOGNITE_AUTH_USERNAME, INCOGNITE_AUTH_TOKEN);
        return INCOGNITE_AUTH_TOKEN;
    }
    public boolean authenticationFacebook(String facebook_username, String facebook_token){
        this.writeToken(facebook_token);
        this.addAccountToManager(facebook_username, facebook_token);
        return true;
    }

    public Intent authenticationGoogle(Context context, FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener ){
        if (this.googleApiClient == null){
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            this.googleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(fragmentActivity, onConnectionFailedListener)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        return intent;
//        this.writeToken(GOOGLE_AUTH_TOKEN);
    }

    public void logout(){
        if (LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut();
        }
        if (auth.getCurrentUser() !=null){
            auth.signOut();
        }
        SharedPreferences preferences = MusicfitPreferencesService.musicfitPreferencesService.openSharedPreferencesFile(PREFERENCES_FILE);
        MusicfitPreferencesService.musicfitPreferencesService.removePreference(preferences, PREFERENCES_TOKEN_KEY);
        this.accountManager.removeAccount(this.account, null, null);
    }
}
