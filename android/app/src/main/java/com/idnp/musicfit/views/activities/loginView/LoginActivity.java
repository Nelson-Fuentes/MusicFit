package com.idnp.musicfit.views.activities.loginView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationConstant;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationService;
import com.idnp.musicfit.presenter.loginPresenter.LoginPresenter;
import com.idnp.musicfit.presenter.loginPresenter.iLoginPresenter;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.activities.registerView.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements iLoginView{

    private iLoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();
        this.loginPresenter = new LoginPresenter(this);
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
        this.loginPresenter.auth("juanperez123", "123456");
    }

    @Override
    public void authValid() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void showError(String error) {
        //Ingresar mensaje de error dentro de un textview
    }
}