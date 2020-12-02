package com.idnp.musicfit.presenter.loginPresenter;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.authenticationService.MusicfitAuthenticationManagerService;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitException;
import com.idnp.musicfit.views.activities.loginView.iLoginView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class LoginPresenter implements iLoginPresenter {

    private iLoginView loginView;
    private CallbackManager callbackManager;

    public LoginPresenter(iLoginView loginView){
        this.loginView = loginView;
        this.callbackManager = CallbackManager.Factory.create();
    }

    public CallbackManager getFacebookCallBackManager(){
        return  this.callbackManager;
    }

    private boolean validateAuthenticationCredentials(String username, String password){
        if (username.trim().length() == 0){
            this.loginView.showError("Ingrese un nombre de usuario.");
            return false;
        } else if (password.length()==0){
            this.loginView.showError("Ingrese su contraseña");
            return false;
        }
        return  true;
    }
    @Override
    public void auth(String username, String password) {
        if (this.validateAuthenticationCredentials(username, password)) {
            try {
                MusicfitAuthenticationManagerService.authenticationService.auth(username, password);
                this.loginView.authValid();
            } catch (Exception e) {
                this.handleException(e);
            }
        }
    }

    @Override
    public void loadRegisterView() {

    }

    @Override
    public void authIncognite() {
        try {
            MusicfitAuthenticationManagerService.authenticationService.authenticationIncognite();
            this.loginView.authIncognite();
        } catch (Exception e){
            this.handleException(e);
        }

    }

    private void handleException(Exception e){
        try{
            throw e;
        } catch (ExecutionException exception) {
            this.loginView.showError(R.string.execution_exception);
        } catch (InterruptedException exception) {
            this.loginView.showError(R.string.interruption_exception);
        } catch (JSONException exception) {
            this.loginView.showError(R.string.json_exception);
        } catch (MusicFitException exception) {
            if (e.getMessage() != null) {
                this.loginView.showError(exception.getMessage());
            } else {
                this.loginView.showError(exception.getStringCode());
            }
        } catch (Exception exception) {
            this.loginView.showError(e.getMessage());
        }
    }

    @Override
    public void authFacebook(String username, String token) {
        MusicfitAuthenticationManagerService.authenticationService.authenticationFacebook(username, token);
        this.loginView.authFacebook();
    }

    @Override
    public void authGoogle(Context context, FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener ) {
        Intent intent = MusicfitAuthenticationManagerService.authenticationService.authenticationGoogle(context, fragmentActivity, onConnectionFailedListener);
        this.loginView.startActivityResult(intent, MusicfitAuthenticationManagerService.GOOGLE_AUTH_RESULT);
//        this.loginView.authGoogle();
//        this.loginView.showError("Soy un error");
    }

    @Override
    public void handleSignInResultGoogle(GoogleSignInResult result) {
        if (result.isSuccess()){
            System.out.println("---------------------------" + result.getSignInAccount().getEmail());
        } else {
            this.loginView.showError(R.string.google_singin_failed);
            System.out.println("---------------------------" + result.getStatus());
        }
    }


}
