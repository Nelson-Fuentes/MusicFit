package com.idnp.musicfit.views.activities.mainView;

import androidx.fragment.app.FragmentActivity;

public interface iMainView {

    public void showNoOpenSessionFoundAction();
    public FragmentActivity getActivityFragment();
    public void logout();
    public void displayUserData(String displayname, String email);

}
