package com.idnp.musicfit.presenter.loginPresenter;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationService;
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
                if (AuthenticationService.authenticationService.auth(username, password)) {
                    this.loginView.authValid();
                } else {
                    this.loginView.showError("Soy un error");
                }
            } catch (ExecutionException e) {
                this.loginView.showError(R.string.execution_exception);
            } catch (InterruptedException e) {
                this.loginView.showError(R.string.interruption_exception);
            } catch (JSONException e) {
                this.loginView.showError(R.string.json_exception);
            } catch (MusicFitException e) {
                if (e.getMessage() != null) {
                    this.loginView.showError(e.getMessage());
                } else {
                    this.loginView.showError(e.getStringCode());
                }
            } catch (Exception e) {
                this.loginView.showError(e.getMessage());
            }
        }
    }

    @Override
    public void loadRegisterView() {

    }

    @Override
    public void authIncognite() {
        if (AuthenticationService.authenticationService.authenticationIncognite()){
            this.loginView.authIncognite();
        } else {
            this.loginView.showError("Soy un error");
        }
    }

    @Override
    public void authFacebook() {
        if (AuthenticationService.authenticationService.authenticationFacebook()){
            this.loginView.authFacebook();
        } else {
            this.loginView.showError("Soy un error");
        }
    }

    @Override
    public void authGoogle() {
        if (AuthenticationService.authenticationService.authenticationGoogle()){
            this.loginView.authGoogle();
        } else {
            this.loginView.showError("Soy un error");
        }
    }

}
