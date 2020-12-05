package com.idnp.musicfit.presenter.registerPresenter;

import com.idnp.musicfit.models.entities.User;

public interface iRegisterPresenter {

    public void registerUser(String firstname, String lastname, String email, String password, String password2);

}
