package com.idnp.musicfit.presenter.mainPresenter;


import com.idnp.musicfit.models.services.authenticationService.AuthenticationService;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.models.services.trainingService.TrainingService;
import com.idnp.musicfit.models.services.userService.UserService;
import com.idnp.musicfit.views.activities.mainView.iMainView;
import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;

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
        AuthenticationService.authenticationService = new AuthenticationService();
        UserService.userService = new UserService();
        TrainingService.trainingService = new TrainingService();
        MusicPlayerService.musicPlayerService = new MusicPlayerService();
    }

    @Override
    public void verifySession() {
        /*
        Se debe de revisar si hay alguna sesion abierta ya sea de un ususario registrado o de un usuario anonimo
        Si no se tiene ninguna sesion creada se debe de salir del activity con finish e ir al activity de login
         */
        if (!AuthenticationService.authenticationService.isLogged()){
            this.mainView.showNoOpenSessionFoundAction();
        }
    }
}
