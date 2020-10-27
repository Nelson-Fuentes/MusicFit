package com.idnp.musicfit.views.fragments.musicPlayerControllerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.services.fragmentManager.FragmentManager;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.presenter.musicPlayerControllerPresenter.MusicPlayerControllerPresenter;
import com.idnp.musicfit.presenter.musicPlayerControllerPresenter.iMusicPlayerControllerPresenter;

public class MusicPlayerControllerFragment extends Fragment implements iMusicPlayerControllerView {

    public static boolean isBigMusicPlayerController = false;

    protected View view;
    private TextView text_state;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private MusicPlayerMiniControllerFragment miniControllerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(this instanceof MusicPlayerMiniControllerFragment)){
            isBigMusicPlayerController = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view =  inflater.inflate(R.layout.fragment_music_player_controller, container, false);
            this.loadComponents();
            this.loadClickListeners();
        }
        return  this.view;
    }

    protected void loadComponents(){
        this.text_state = (TextView) this.view.findViewById(R.id.text_state);
        this.playButton = (Button) this.view.findViewById(R.id.play_button);
        this.pauseButton = (Button) this.view.findViewById(R.id.pause_button);
        this.stopButton = (Button) this.view.findViewById(R.id.stop_button);
    }

    protected void loadClickListeners(){
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.play();
            }
        });
        this.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.pause();
            }
        });
        this.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.stop();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Fragment fragment: FragmentManager.fragmentManager.getFragments()){
            if (fragment instanceof  MusicPlayerMiniControllerFragment && fragment!=this){
                this.miniControllerFragment = (MusicPlayerMiniControllerFragment) fragment;
            }
        }
        if (MusicPlayerControllerPresenter.musicPlayerControllerPresenter==null)
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter  = new MusicPlayerControllerPresenter(this);
        if (this instanceof  MusicPlayerMiniControllerFragment){
            if (!isBigMusicPlayerController)
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.setView(this);
        } else {
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter.setView(this);
        }
    }


    @Override
    public void play() {
        this.text_state.setText("PLAY");
    }

    @Override
    public void pause() {
        this.text_state.setText("PAUSE");
    }

    @Override
    public void stop() {
        this.text_state.setText("STOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!(this instanceof  MusicPlayerMiniControllerFragment)){
            isBigMusicPlayerController =false;
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter.setView(this.miniControllerFragment);
        }
/*        boolean flag = true;
        for (Fragment fragment: FragmentManager.fragmentManager.getFragments()){
            if (fragment instanceof  MusicPlayerControllerFragment && !(fragment instanceof MusicPlayerMiniControllerFragment)){
                flag = false;
            }
        }
        if (flag && !(this instanceof MusicPlayerMiniControllerFragment))
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter.setView(this.miniControllerFragment);
*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}