package com.idnp.musicfit.presenter.registerPresenter;

import android.content.Intent;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationConstant;
import com.idnp.musicfit.models.services.userService.UserService;
import com.idnp.musicfit.views.activities.registerView.iRegisterView;

public class RegisterPresenter implements iRegisterPresenter {

    private iRegisterView registerView;
    private static String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public RegisterPresenter(iRegisterView registerView){
        this.registerView = registerView;
    }

    public boolean validateUserData(String username,  String firstname, String lastname, String email, String password, String password2){
        if (username.isEmpty()){
            this.registerView.showError(R.string.username_error_label);
            return false;
        }
        if (firstname.isEmpty()){
            this.registerView.showError(R.string.firstname_error_label);
            return false;
        }
        if (lastname.isEmpty()){
            this.registerView.showError(R.string.lastname_error_label);
            return false;
        }
        if (email.isEmpty() || !email.matches(emailRegex)){
            this.registerView.showError(R.string.email_error_label);
            return false;
        }
        if (password.isEmpty()){
            this.registerView.showError(R.string.password_error_label);
            return false;
        }
        if (!password.equals(password2)){
            this.registerView.showError(R.string.password_2_error_label);
            return false;
        }
        return true;
    }

    @Override
    public void registerUser(String username,  String firstname, String lastname, String email, String password, String password2) {
        if (validateUserData(username, firstname, lastname, email, password, password2)){
            User user_registered = UserService.userService.registerUser(username, firstname, lastname, email, password);
            if (user_registered == null) {
                this.registerView.showError(0); //Notificar que no se creo el usuario y porque
            } else {

            }
            this.registerView.successfullyRegister();
        }
    }
}
