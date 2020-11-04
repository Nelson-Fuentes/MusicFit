package com.idnp.musicfit.models.services.authenticationService;

import com.idnp.musicfit.models.entities.User;

public class AuthenticationService {

    public static AuthenticationService authenticationService;


    private static User currentUser;
    public static int ERROR;

    public  boolean auth(String username, String password){
        /*
        Se debe validar por interent, si no tuvo exito retorna falso.
         */
        currentUser = new User("JuanPerez", "Juan", "Perez", "juan@coree.com");
        return  true;
    }

    public boolean isLogged(){
        return currentUser!=null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean autheticationIncognite(){
        currentUser = new User("incognite", "Modo", "Incognito", "musicfit@noreply.com");
        return true;
    }
}
