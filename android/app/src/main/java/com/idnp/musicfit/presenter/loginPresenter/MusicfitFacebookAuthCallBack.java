package com.idnp.musicfit.presenter.loginPresenter;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.idnp.musicfit.R;
import com.idnp.musicfit.views.toastManager.ToastManager;

public class MusicfitFacebookAuthCallBack implements FacebookCallback<LoginResult> {

    private iLoginPresenter loginPresenter;

    public MusicfitFacebookAuthCallBack(iLoginPresenter loginPresenter){
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        this.loginPresenter.authFacebook(loginResult.getAccessToken().getToken());
//        LoginManager.getInstance().logOut();
    }

    @Override
    public void onCancel() {
        ToastManager.toastManager.showToast(R.string.facebook_login_cancelled);
    }

    @Override
    public void onError(FacebookException error) {
        ToastManager.toastManager.showToast(R.string.facebook_login_error);

    }
}
