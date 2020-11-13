package com.idnp.musicfit.views.activities.loginView;

public interface iLoginView {

    public void authValid();
    public void showError(int error);
    public void showError(String error);
    public void authIncognite();
    public void authFacebook();
    public void authGoogle();
}
