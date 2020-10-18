package com.idnp.musicfit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idnp.musicfit.R;
import com.idnp.musicfit.auth.AuthenticationConstant;
import com.idnp.musicfit.models.User;
import com.idnp.musicfit.remoteServices.MusicFitService;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();
    }

    public void registerUserClickListener(View view){
        if (this.validateUserRegisterForm()){
            MusicFitService service = new MusicFitService();
            User user = service.registerUser();
            if (user == null){
                /*
                Notificar que no se creo el usuario y porque
                 */
            } else {
                Intent intent = new Intent();
                intent.putExtra(AuthenticationConstant.USERNAME_TAG_KEY, "JuanPerez");
                intent.putExtra(AuthenticationConstant.PASSWORD_TAG_KEY, "123456");
                this.setResult(RESULT_OK, intent);
                this.finish();
            }
        }
    }

    private boolean validateUserRegisterForm(){
        /*
        Se deben validar todos los campos del formulario
        Los errors deben indicarse en la interfaz grafica.
         */
        return  true;
    }
}