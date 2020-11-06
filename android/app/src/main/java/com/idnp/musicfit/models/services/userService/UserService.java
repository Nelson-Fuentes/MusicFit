package com.idnp.musicfit.models.services.userService;

import com.idnp.musicfit.models.entities.User;

public class UserService {

    public static UserService userService;

    public User registerUser(String username,  String firstname, String lastname, String email, String password){
        return new User(", ", ", ", "", "");
    }
}
