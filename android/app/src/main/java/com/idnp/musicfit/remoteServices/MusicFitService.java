package com.idnp.musicfit.remoteServices;

import com.idnp.musicfit.models.User;

public class MusicFitService {
    /*
    Esta clase no se como se implemnteria asi que esta sujeta a cambios
     */

    public User registerUser(){
        /*
        Si ocurrio un error regresar null
         */

        return new User("JuanPerez", "Juan Carlos", "Perez Perez", "juan@perez.com");
    }
}
