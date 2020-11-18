package com.idnp.musicfit.models.services.authenticationService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicfitAuthenticationService  extends Service {
    private MusicfitAuthenticator authenticator;
    @Override
    public void onCreate() {
        this.authenticator = new MusicfitAuthenticator(this);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return this.authenticator.getIBinder();
    }
}