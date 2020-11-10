package com.idnp.musicfit.views.fragments.musicPlayerControllerView;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.musicPlayerControllerPresenter.MusicPlayerControllerPresenter;

public class MusicPlayerControllerFragment extends Fragment implements iMusicPlayerControllerView {

    public static boolean isBigMusicPlayerController = false;

    public static final int STOPPED = -1;
    public static final int PAUSED = 0;
    public static final int PLAYED = 1;

    public View view;
    //private TextView text_state;
    protected ImageButton randomButton,backButton,playButton,advanceButton,repeatButton;
    private ImageView imageSong;
    MusicPlayerControllerPresenter musicPlayerControllerPresenter;
    public MusicPlayerMiniControllerFragment miniControllerFragment;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MusicPlayerControllerPresenter(this);
        if (!(this instanceof MusicPlayerMiniControllerFragment)){
            isBigMusicPlayerController = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view =  inflater.inflate(R.layout.fragment_music_player_controller, container, false);
        //CARGANDO BOTON DE RANDOM
        this.playButton = (ImageButton) view.findViewById(R.id.random_button_player);
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.play();
            }
        });
        //CARGANDO BOTON DE BACK
        this.playButton = (ImageButton) view.findViewById(R.id.back_button_player);
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.play();
            }
        });
        //CARGANDO BOTON DE PLAY
        this.playButton = (ImageButton) view.findViewById(R.id.play_button_player);
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.play();
            }
        });
        //CARGANDO BOTON DE ADVANCE
        this.repeatButton = (ImageButton) view.findViewById(R.id.advance_button_player);
        this.repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.pause();
            }
        });
        //CARGANDO BOTON DE REPEAT
        this.repeatButton = (ImageButton) view.findViewById(R.id.repeat_button_player);
        this.repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.pause();
            }
        });
        //CARGANDO IMAGEN DE REPRODUCTOR
        this.imageSong = (ImageView) view.findViewById(R.id.image_music_player);

        return  this.view;
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

    public void loadSelectedMusic(){
        Song music=MusicPlayerControllerPresenter.musicPlayerControllerPresenter.getCurrentMusic();
        mediaPlayer=MediaPlayer.create(getContext(),music.getMusic());
    }
    @Override
    public void play() {
        if(mediaPlayer==null) {
            loadSelectedMusic();
        }
        this.playButton.setBackgroundResource(R.drawable.button_player_pause_mp3);
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pause();
        }
        else{
            mediaPlayer.start();
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter.getCurrentMusic();
        }

    }

    @Override
    public void pause() {
        this.playButton.setBackgroundResource(R.drawable.button_player_play_mp3);

    }

    @Override
    public void stop() {
        //this.text_state.setText("STOP");
    }

    @Override
    public void random() {

    }

    @Override
    public void repeat() {

    }

    @Override
    public void back() {

    }

    @Override
    public void advance() {

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