package com.idnp.musicfit.presenter.loginPresenter;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

public interface iLoginPresenter {

    public void auth(String username, String password);
    public void loadRegisterView();
    public void authIncognite();
    public void authFacebook(String username, String token);
    public void authGoogle(Context context, FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener );
    public void handleSignInResultGoogle(GoogleSignInResult result);
    public CallbackManager getFacebookCallBackManager();
}
