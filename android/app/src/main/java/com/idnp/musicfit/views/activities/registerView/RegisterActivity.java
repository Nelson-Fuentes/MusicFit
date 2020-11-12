package com.idnp.musicfit.views.activities.registerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationConstant;
import com.idnp.musicfit.models.services.musicFitRemoteService.MusicfitConnection;
import com.idnp.musicfit.presenter.registerPresenter.RegisterPresenter;
import com.idnp.musicfit.presenter.registerPresenter.iRegisterPresenter;
import com.idnp.musicfit.views.toastManager.ToastManager;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity implements iRegisterView {

    private iRegisterPresenter registerPresenter;
    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText password2EditText;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();
        this.registerPresenter = new RegisterPresenter(this);
        this.loadComponentes();
    }

    private void loadComponentes(){
        this.usernameEditText = (EditText) this.findViewById(R.id.username_edit_text);
        this.firstNameEditText = (EditText) this.findViewById(R.id.firstname_edit_text);
        this.lastNameEditText = (EditText) this.findViewById(R.id.lastname_edit_text);
        this.emailEditText = (EditText) this.findViewById(R.id.email_edit_text);
        this.passwordEditText = (EditText) this.findViewById(R.id.password_edit_text);
        this.password2EditText = (EditText) this.findViewById(R.id.password2_edit_text);
        this.errorTextView = (TextView) this.findViewById(R.id.error_text_view);
    }

    public void registerUserClickListener(View view){
        this.errorTextView.setText("");
        this.registerPresenter.registerUser(
                this.usernameEditText.getText().toString(),
                this.firstNameEditText.getText().toString(),
                this.lastNameEditText.getText().toString(),
                this.emailEditText.getText().toString(),
                this.passwordEditText.getText().toString(),
                this.password2EditText.getText().toString()
        );
    }

    @Override
    public void showError(int error) {
        this.errorTextView.setText(this.getString(error));
    }

    @Override
    public void cancel() {
        this.setResult(RESULT_CANCELED, new Intent());
        this.finish();
    }

    @Override
    public void successfullyRegister() {
        ToastManager.toastManager.showToast(R.string.user_registered);
        Intent intent = new Intent();
        intent.putExtra(AuthenticationConstant.USERNAME_TAG_KEY, "JuanPerez");
        intent.putExtra(AuthenticationConstant.PASSWORD_TAG_KEY, "123456");
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void showError(String error) {
        this.errorTextView.setText(error);
    }
}