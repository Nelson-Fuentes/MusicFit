package com.idnp.musicfit.models.services.userService;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitException;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitResponse;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitService;
import com.idnp.musicfit.models.services.musicfitFirebase.MusicfitFireBase;
import com.idnp.musicfit.presenter.registerPresenter.iRegisterPresenter;
import com.idnp.musicfit.views.toastManager.ToastManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;

public class UserService {

    public static UserService userService;
    private static final String USERNAME_LABEL = "username";
    private static final String PASSWORD_LABEL = "password";
    private static final String FIRST_NAME_LABEL = "first_name";
    private static final String LAST_NAME_LABEL = "last_name";
    private static final String EMAIL_LABEL = "email";
    private static final String USER_PATH_FIREBASE_REALTIME = "users";
    private DatabaseReference fireBase;
    private FirebaseAuth auth;

    public UserService(){
        MusicfitFireBase fireBase = new MusicfitFireBase();
        this.fireBase = fireBase.getChild(USER_PATH_FIREBASE_REALTIME);
        this.auth = fireBase.getAuth();
    }

    public User registerUser(String firstname, String lastname, String email, String password , iRegisterPresenter presenter) throws Exception {
        User new_user = new User(firstname, lastname);
        this.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            saveDataUser(task.getResult().getUser().getUid(), new_user);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstname + " " + lastname).build();
                            task.getResult().getUser().updateProfile(profileUpdates);
                            auth.signOut();
                            presenter.handleRegisterSuccess();
                        } else {
                            ToastManager.toastManager.showToast(task.getException().getMessage());
                        }
                    }
                });
        return new_user;
    }

    public void saveDataUser(String uid, User user){
        fireBase.child(uid).setValue(user);
    }

    public void getDataUser(String uid){

    }
}
