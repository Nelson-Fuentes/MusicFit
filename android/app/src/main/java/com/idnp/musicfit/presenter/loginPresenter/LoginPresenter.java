package com.idnp.musicfit.presenter.loginPresenter;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.authenticationService.MusicfitAuthenticationManagerService;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitException;
import com.idnp.musicfit.views.activities.loginView.iLoginView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class LoginPresenter implements iLoginPresenter {

    private iLoginView loginView;

    public LoginPresenter(iLoginView loginView){
        this.loginView = loginView;
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
    public void authFacebook() {
        if (MusicfitAuthenticationManagerService.authenticationService.authenticationFacebook()){
            this.loginView.authFacebook();
        } else {
            this.loginView.showError("Soy un error");
        }
    }

    @Override
    public void authGoogle() {
        if (MusicfitAuthenticationManagerService.authenticationService.authenticationGoogle()){
            this.loginView.authGoogle();
        } else {
            this.loginView.showError("Soy un error");
        }
    }

}
