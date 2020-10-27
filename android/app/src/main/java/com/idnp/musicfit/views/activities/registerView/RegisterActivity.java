package com.idnp.musicfit.views.activities.registerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationConstant;
import com.idnp.musicfit.models.entities.User;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicFitService;
import com.idnp.musicfit.presenter.registerPresenter.RegisterPresenter;
import com.idnp.musicfit.presenter.registerPresenter.iRegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements iRegisterView {

    private iRegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();
        this.registerPresenter = new RegisterPresenter(this);
    }

    public void registerUserClickListener(View view){
        this.registerPresenter.registerUser(new User("", "", "", "")); //cargar datos con lo que se recibe del formulario
    }

    @Override
    public void showError(String error) {
        //Muestra errores en la interfaz grafica
    }

    @Override
    public void cancel() {
        this.setResult(RESULT_CANCELED, new Intent());
        this.finish();
    }

    @Override
    public void successfullyRegister() {
        Intent intent = new Intent();
        intent.putExtra(AuthenticationConstant.USERNAME_TAG_KEY, "JuanPerez");
        intent.putExtra(AuthenticationConstant.PASSWORD_TAG_KEY, "123456");
        this.setResult(RESULT_OK, intent);
        this.finish();
    }
}