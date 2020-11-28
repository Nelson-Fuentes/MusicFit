package com.idnp.musicfit.views.activities.loginView;

import android.content.Intent;

public interface iLoginView {

    public void authValid();
    public void showError(int error);
    public void showError(String error);
    public void authIncognite();
    public void authFacebook();
    public void authGoogle();
    public void startActivityResult(Intent intent, int code);
}
