package com.idnp.musicfit.models.services.musicfitFirebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MusicfitFireBase {
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    public MusicfitFireBase(){
        this.database = FirebaseDatabase.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    public DatabaseReference getReference(){
        return this.database.getReference();
    }

    public DatabaseReference getChild(String path){
        return this.getReference().child(path);
    }

    public FirebaseAuth getAuth(){
        return  this.auth;
    }

}
