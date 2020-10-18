package com.idnp.musicfit.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idnp.musicfit.MainActivity;
import com.idnp.musicfit.R;
import com.idnp.musicfit.auth.AuthenticationConstant;
import com.idnp.musicfit.auth.AuthenticationService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();
    }

    public void startRegisterActivity(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivityForResult(intent, AuthenticationConstant.REGISTER_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == AuthenticationConstant.REGISTER_REQUEST_CODE){
                String username = (String) data.getExtras().get(AuthenticationConstant.USERNAME_TAG_KEY);
                String password = (String) data.getExtras().get(AuthenticationConstant.PASSWORD_TAG_KEY);
            }
        }

    }

    public void login(View view){
        AuthenticationService service = new AuthenticationService();
        if (service.auth("juanperez123", "123456")){
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
        } else {
            /*
            Notificar al usaurio que no se pudo iniciar una sesion y porque
             */
        }
    }
}