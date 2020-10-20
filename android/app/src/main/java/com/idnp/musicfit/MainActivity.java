package com.idnp.musicfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.idnp.musicfit.activities.LoginActivity;
import com.idnp.musicfit.auth.AuthenticationService;
import com.idnp.musicfit.fragments.FragmentManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.validateUserSession();

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

        this.prepareMetaData();
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


    private void validateUserSession(){
        /*
        Se debe de revisar si hay alguna sesion abierta ya sea de un ususario registrado o de un usuario anonimo
        Si no se tiene ninguna sesion creada se debe de salir del activity con finish e ir al activity de login
         */
        AuthenticationService service = new AuthenticationService();
        if (!service.isLogged()){
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }

    private void prepareMetaData(){
        /*
        Se deben de alistar todas las variables en este y otros componentes segun la coniguración que se necesite.
         */
        FragmentManager.fragmentManager = new FragmentManager(this);
    }

}