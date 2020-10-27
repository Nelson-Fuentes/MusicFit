package com.idnp.musicfit.views.fragments.musicPlayerControllerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.musicPlayerControllerPresenter.MusicPlayerControllerPresenter;

public class MusicPlayerMiniControllerFragment extends MusicPlayerControllerFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view =  inflater.inflate(R.layout.fragment_music_player_mini_controller, container, false);
            this.loadComponents();
            this.loadClickListeners();
        }
        return  this.view;
    }
    @Override
    public void play() {
        super.play();
        FragmentManager.fragmentManager.show(this);
    }

    public void pause() {
        super.pause();
        FragmentManager.fragmentManager.show(this);
    }

    @Override
    public void stop() {
        super.stop();
        FragmentManager.fragmentManager.hide(this);
    }

}