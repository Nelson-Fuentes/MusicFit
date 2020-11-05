package com.idnp.musicfit.presenter.loginPresenter;

public interface iLoginPresenter {

    public void auth(String username, String password);
    public void loadRegisterView();
    public void authIncognite();
    public void authFacebook();
    public void authGoogle();

}
