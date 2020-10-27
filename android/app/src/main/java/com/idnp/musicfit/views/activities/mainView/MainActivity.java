package com.idnp.musicfit.views.activities.mainView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.idnp.musicfit.R;
import com.idnp.musicfit.presenter.mainPresenter.MainPresenter;
import com.idnp.musicfit.presenter.mainPresenter.iMainPresenter;
import com.idnp.musicfit.views.activities.loginView.LoginActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_training, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        this.mainPresenter = new MainPresenter(this);
        this.mainPresenter.loadDefaultServices();
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
}