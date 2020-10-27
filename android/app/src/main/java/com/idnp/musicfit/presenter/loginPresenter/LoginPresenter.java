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

    @Override
    public void auth(String username, String password) {
        if (AuthenticationService.authenticationService.auth(username, password)){
            this.loginView.authValid();
        } else {
            this.loginView.showError("Soy un error"); //Notificar al usaurio que no se pudo iniciar una sesion y porque
        }
    }

    @Override
    public void loadRegisterView() {

    }
}
