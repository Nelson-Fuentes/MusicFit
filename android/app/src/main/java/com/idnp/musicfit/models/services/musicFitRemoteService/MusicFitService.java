package com.idnp.musicfit.models.services.musicFitRemoteService;

import com.idnp.musicfit.models.entities.User;

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
