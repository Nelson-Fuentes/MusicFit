package com.idnp.musicfit.auth;

import com.idnp.musicfit.models.User;

public class AuthenticationService {


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
}
