package com.idnp.musicfit.views.activities.loginView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.authenticationService.AuthenticationConstant;
import com.idnp.musicfit.presenter.loginPresenter.LoginPresenter;
import com.idnp.musicfit.presenter.loginPresenter.iLoginPresenter;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.activities.registerView.RegisterActivity;
import com.idnp.musicfit.views.toastManager.ToastManager;

public class LoginActivity extends AppCompatActivity implements iLoginView{

    private iLoginPresenter loginPresenter;
    private TextView registerTextView;
    private TextView errorTextView;
    private EditText usernameEditView;
    private EditText passwordEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();
        this.loginPresenter = new LoginPresenter(this);
        this.loadComponents();
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
                this.loginPresenter.auth(username, password);
            }
        }

    }

    public void login(View view){
        String username = this.usernameEditView.getText().toString();
        String password = this.passwordEditView.getText().toString();
        this.loginPresenter.auth(username, password);
    }

    @Override
    public void authValid() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    public void loadComponents(){
        this.registerTextView = (TextView) this.findViewById(R.id.go_to_register_edit_text);
        this.transformRegisterTexViewToBicolor(this.registerTextView);
        this.usernameEditView = (EditText) this.findViewById(R.id.username_edit_text);
        this.passwordEditView = (EditText) this.findViewById(R.id.password_edit_text);
        this.errorTextView = (TextView) this.findViewById(R.id.error_text_view);
    }

    private void transformRegisterTexViewToBicolor(TextView textView){
        String goRegister = (String) textView.getText();
        String registerString = (String) this.getString(R.string.register_label);
        int start_first = 0, last_first = goRegister.indexOf(registerString);
        int last_second = last_first + registerString.length();
        int last_third = goRegister.length();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        this.addStringToSpannalbeBuilder(goRegister.substring(start_first, last_first), builder, R.color.black);
        this.addStringToSpannalbeBuilder(goRegister.substring(last_first, last_second), builder, R.color.main_color);
        this.addStringToSpannalbeBuilder(goRegister.substring(last_second, last_third), builder, R.color.black);
        textView.setText( builder, TextView.BufferType.SPANNABLE);
    }

    private void addStringToSpannalbeBuilder(String text, SpannableStringBuilder builder, int color){
        SpannableString str1= new SpannableString(text);
        str1.setSpan(new ForegroundColorSpan(getColor(color)), 0, str1.length(), 0);
        builder.append(str1);
    }

    @Override
    public void showError(String error) {
        this.errorTextView.setText(error);
    }

    @Override
    public void authIncognite() {
        this.authValid();
        ToastManager.toastManager.showToast(R.string.login_incognite);
    }

    @Override
    public void authFacebook() {
        this.authValid();
        ToastManager.toastManager.showToast(R.string.login_facebook);
    }

    @Override
    public void authGoogle() {
        this.authValid();
        ToastManager.toastManager.showToast(R.string.login_google);
    }

    public void authIncognite(View view){
        this.loginPresenter.authIncognite();
    }

    public void authFacebook(View view){
        this.loginPresenter.authFacebook();
    }

    public void authGoogle(View view){
        this.loginPresenter.authGoogle();
    }
}