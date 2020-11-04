package com.idnp.musicfit.presenter.loginPresenter;

import android.content.Intent;

import com.idnp.musicfit.models.services.authenticationService.AuthenticationService;
import com.idnp.musicfit.views.activities.loginView.iLoginView;
import com.idnp.musicfit.views.activities.mainView.MainActivity;

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
        if (this.validateAuthenticationCredentials(username, password))
            if (AuthenticationService.authenticationService.auth(username, password)){
                this.loginView.authValid();
            } else {
                this.loginView.showError("Soy un error");
            }
    }

    @Override
    public void loadRegisterView() {

    }

    @Override
    public void authIncognite() {
        if (AuthenticationService.authenticationService.autheticationIncognite()){
            this.loginView.authValid();
        } else {
            this.loginView.showError("Soy un error");
        }
    }

}
