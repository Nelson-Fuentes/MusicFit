package com.idnp.musicfit.presenter.mainPresenter;


import com.idnp.musicfit.models.services.authenticationService.MusicfitAuthenticationManagerService;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitService;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.models.services.musicfitPreferences.MusicfitPreferencesService;
import com.idnp.musicfit.models.services.userService.UserService;
import com.idnp.musicfit.views.activities.mainView.iMainView;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.views.toastManager.ToastManager;

public class MainPresenter implements iMainPresenter {
    private iMainView mainView;

    public MainPresenter(iMainView mainView){
        this.mainView = mainView;
    }

    @Override
    public void loadDefaultServices() {
        /*
        Se deben de alistar todas las variables en este y otros componentes segun la coniguración que se necesite.
         */
        FragmentManager.fragmentManager = new FragmentManager(this.mainView.getActivityFragment());
        MusicfitAuthenticationManagerService.authenticationService = new MusicfitAuthenticationManagerService(this.mainView.getActivityFragment().getApplicationContext());
        UserService.userService = new UserService();

        MusicPlayerService.musicPlayerService = new MusicPlayerService();
        ToastManager.toastManager = new ToastManager(this.mainView.getActivityFragment().getApplicationContext());
        MusicFitService.musicfitService = new MusicFitService();
        MusicfitPreferencesService.musicfitPreferencesService = new MusicfitPreferencesService(this.mainView.getActivityFragment().getApplicationContext());
    }

    @Override
    public void verifySession() {
        /*
        Se debe de revisar si hay alguna sesion abierta ya sea de un ususario registrado o de un usuario anonimo
        Si no se tiene ninguna sesion creada se debe de salir del activity con finish e ir al activity de login
         */
        if (!MusicfitAuthenticationManagerService.authenticationService.isLogged()){
            this.mainView.showNoOpenSessionFoundAction();
        }
    }

    @Override
    public void logout() {
        MusicfitAuthenticationManagerService.authenticationService.logout();
        this.mainView.logout();
    }
}
