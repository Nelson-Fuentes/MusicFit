package com.idnp.musicfit.views.activities.loginView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

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

    public void loadComponents(){
        TextView textView = (TextView) this.findViewById(R.id.go_to_register_edit_text);
        String goRegister = (String) textView.getText();
        String registerString = (String) this.getString(R.string.register_label);
        int start_first = 0, last_first = goRegister.indexOf(registerString);
        int last_second = last_first + registerString.length();
        int last_third = goRegister.length();

        System.out.println(start_first + " -------------------" + last_first + "------------" + last_second  + "--------------" + last_third);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1= new SpannableString(goRegister.substring(start_first, last_first));
        str1.setSpan(new ForegroundColorSpan(getColor(R.color.black)), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2= new SpannableString(goRegister.substring(last_first, last_second));
        str2.setSpan(new ForegroundColorSpan(getColor(R.color.main_color)), 0, str2.length(), 0);
        builder.append(str2);

        SpannableString str3= new SpannableString(goRegister.substring(last_second, last_third));
        str3.setSpan(new ForegroundColorSpan(getColor(R.color.black)), 0, str3.length(), 0);
        builder.append(str3);

        textView.setText( builder, TextView.BufferType.SPANNABLE);

//        String color = this.getResources().getString(R.color.main_color);
//        textView.setText(Html.fromHtml(goRegister), TextView.BufferType.SPANNABLE);
    }

    @Override
    public void showError(String error) {
        //Ingresar mensaje de error dentro de un textview
    }
}