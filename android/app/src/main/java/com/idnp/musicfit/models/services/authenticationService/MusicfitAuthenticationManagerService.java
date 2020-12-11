package com.idnp.musicfit.models.services.authenticationService;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.musicfitFirebase.MusicfitFireBase;
import com.idnp.musicfit.models.services.musicfitPreferences.MusicfitPreferencesService;
import com.idnp.musicfit.models.services.userService.UserService;
import com.idnp.musicfit.presenter.loginPresenter.iLoginPresenter;
import com.idnp.musicfit.views.toastManager.ToastManager;

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
    private static GoogleApiClient googleApiClient;
    private FirebaseAuth auth;
    private Context context;

    public MusicfitAuthenticationManagerService(Context context){
        this.auth = new MusicfitFireBase().getAuth();
        this.accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length>0){
            this.account = accounts[0];
        }
        this.context = context;
        if (this.googleApiClient== null){
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            this.googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        if (!this.googleApiClient.isConnected()){
            this.googleApiClient.connect();
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

    public String getCurrentUserId(){
        if (!this.isLogged() || this.isIncognite())
            return null;
        return this.auth.getCurrentUser().getUid();
    }

    public String getCurrentUserEmail(){
        if (!this.isLogged() || this.isIncognite())
            return null;
        return this.auth.getCurrentUser().getEmail();
    }

    public String getCurrentUserDisplayName(){
        if (!this.isLogged() || this.isIncognite())
            return null;
        return this.auth.getCurrentUser().getDisplayName();
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
    public void authenticationFacebook(String facebook_token, iLoginPresenter loginPresenter){
        AuthCredential credential = FacebookAuthProvider.getCredential(facebook_token);
        this.auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    writeToken(facebook_token);
                    FirebaseUser user = task.getResult().getUser();
                    addAccountToManager(user.getDisplayName(), facebook_token);
                    UserService.userService.saveDataUser(user.getUid(), new User(user.getDisplayName(), ""));
                    loginPresenter.handleSignInFacebookSucess();
                }
            }
        });
    }

    public Intent authenticationGoogle(Context context, FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener ){
        if (this.googleApiClient == null){
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            this.googleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(fragmentActivity, onConnectionFailedListener)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        return intent;
    }

    public void handleSuccessAuthenticationGoogle(GoogleSignInResult result, iLoginPresenter loginPresenter){
        AuthCredential credential = GoogleAuthProvider.getCredential(result.getSignInAccount().getIdToken(), null);
        this.auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    writeToken(result.getSignInAccount().getIdToken());
                    FirebaseUser user = task.getResult().getUser();
                    addAccountToManager(user.getDisplayName(), result.getSignInAccount().getIdToken());
                    UserService.userService.saveDataUser(user.getUid(), new User(user.getDisplayName(), ""));
                    loginPresenter.handleSignInGoogleSucess();
                } else {
                    ToastManager.toastManager.showToast(task.getException().getMessage());
                }
            }
        });
    }

    public void logout(){
        Auth.GoogleSignInApi.signOut(this.googleApiClient);
        if (LoginManager.getInstance() != null){
            if(AccessToken.getCurrentAccessToken()!=null){
                AccessToken.setCurrentAccessToken(null);
            }
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
