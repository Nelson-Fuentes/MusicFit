package com.idnp.musicfit.models.services.fragmentManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.idnp.musicfit.R;

import java.util.List;

public class FragmentManager {


    public static FragmentManager fragmentManager;
    private static final String TAG_FRAGMENT = "-FRAGMENT";

    private FragmentActivity activity;
    private int i;

    public FragmentManager(FragmentActivity activity){
        this.activity = activity;
        this.i = 0;
    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, i+TAG_FRAGMENT);
        transaction.addToBackStack(i+TAG_FRAGMENT);
        i++;
        transaction.commitAllowingStateLoss();
    }

    public void remove(Fragment fragment){
        this.activity.getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public List<Fragment> getFragments(){
        return this.activity.getSupportFragmentManager().getFragments();
    }


    public void hide(Fragment fragment){
        this.activity.getSupportFragmentManager().beginTransaction().hide(fragment).commit();
    }

    public void show(Fragment fragment){
        this.activity.getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

}
