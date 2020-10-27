package com.idnp.musicfit.presenter.registerPresenter;

import android.content.Intent;

import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationConstant;
import com.idnp.musicfit.models.services.userService.UserService;
import com.idnp.musicfit.views.activities.registerView.iRegisterView;

public class RegisterPresenter implements iRegisterPresenter {

    private iRegisterView registerView;

    public RegisterPresenter(iRegisterView registerView){
        this.registerView = registerView;
    }

    public boolean validateUserData(User user){
        if (!true){ //validacion de un atributo
            this.registerView.showError("Soy un error.");
            return false;
        }
        return true;
    }

    @Override
    public void registerUser(User user) {
        if (validateUserData(user)){
            User user_registered = UserService.userService.registerUser(user);
            if (user_registered == null) {
                this.registerView.showError("Soy un error."); //Notificar que no se creo el usuario y porque
            } else {

            }
            this.registerView.successfullyRegister();
        }
    }
}
