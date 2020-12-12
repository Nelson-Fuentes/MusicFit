package com.idnp.musicfit.views.activities.mainView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.material.navigation.NavigationView;
import com.idnp.musicfit.R;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.mainPresenter.MainPresenter;
import com.idnp.musicfit.presenter.mainPresenter.iMainPresenter;
import com.idnp.musicfit.views.activities.loginView.LoginActivity;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity  implements  iMainView{

    private AppBarConfiguration mAppBarConfiguration;
    private iMainPresenter mainPresenter;
    private TextView email_textView;
    private TextView displayname_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_training, R.id.nav_training_list, R.id.nav_music_list_player)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        this.mainPresenter = new MainPresenter(this);
        this.mainPresenter.loadDefaultServices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mainPresenter.verifySession();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void showNoOpenSessionFoundAction() {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public FragmentActivity getActivityFragment() {
        return this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        for (Fragment fragment: FragmentManager.fragmentManager.getFragments()){
            if (fragment instanceof TrainingReportFragment){
                FragmentManager.fragmentManager.remove(fragment);
            }
        }
    }

    public void logout(View view){
        this.mainPresenter.logout();
    }

    public void logout(){
        this.showNoOpenSessionFoundAction();
    }

    @Override
    public void displayUserData(String displayname, String email) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        this.email_textView = (TextView) headerView.findViewById(R.id.current_user_email);
        this.displayname_textView = (TextView) headerView.findViewById(R.id.current_user_displayname);
        if (email == null)
            email = "";
        if (displayname == null)
            displayname = "";
        this.email_textView.setText(email);
        this.displayname_textView.setText(displayname);
    }

}