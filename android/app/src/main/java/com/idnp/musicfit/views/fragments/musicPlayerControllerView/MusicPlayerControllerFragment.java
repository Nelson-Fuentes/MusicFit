package com.idnp.musicfit.views.fragments.musicPlayerControllerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.MusicPlayList;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.musicPlayerControllerPresenter.MusicPlayerControllerPresenter;

import java.util.concurrent.TimeUnit;

public class MusicPlayerControllerFragment extends Fragment implements iMusicPlayerControllerView {

    public static boolean isBigMusicPlayerController = false;

    public static final int STOPPED = -1;
    public static final int PAUSED = 0;
    public static final int PLAYED = 1;

    public View view;
    //private TextView text_state;
    protected ImageButton randomButton,backButton,playButton,advanceButton,repeatButton;
    private ImageView imageSong;
    private TextView name_song,name_artist,currentTime,completeTime;
    private MusicPlayerControllerPresenter musicPlayerControllerPresenter;
    public MusicPlayerMiniControllerFragment miniControllerFragment;
    private MediaPlayer mediaPlayer;
    private SeekBar timeLine;
    private Handler handler=new Handler();
    private int startTime;
    private int endTime;

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
        //CARGANDO BOTON DE PLAY
        this.playButton = (ImageButton) view.findViewById(R.id.play_button_player);
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.play();
            }
        });
        //CARGANDO BOTON DE BACK
        this.backButton = (ImageButton) view.findViewById(R.id.back_button_player);
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.back();
            }
        });
        //CARGANDO BOTON DE ADVANCE
        this.advanceButton = (ImageButton) view.findViewById(R.id.advance_button_player);
        this.advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.advance();
            }
        });

        //CARGANDO BOTON DE RANDOM
        this.randomButton = (ImageButton) view.findViewById(R.id.random_button_player);
        this.randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.random();

            }
        });
        //CARGANDO BOTON DE REPEAT
        this.repeatButton = (ImageButton) view.findViewById(R.id.repeat_button_player);
        this.repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.repeat();
            }
        });
        //CARGANDO IMAGEN DE REPRODUCTOR
        this.imageSong = (ImageView) view.findViewById(R.id.image_music_player);
        //NOMBRE DE LA CANCION
        this.name_song = (TextView) view.findViewById(R.id.name_song_player);
        //NOMBRE DEL ARTISTA DE LA CANCION
        this.name_artist = (TextView) view.findViewById(R.id.name_artist_player);
        //LINEA DE TIEMPO DE LA CANCION
        this.timeLine=(SeekBar) view.findViewById(R.id.time_line_player);
        //TIEMPO ACTUAL DE LA CANCION
        this.currentTime=(TextView) view.findViewById(R.id.current_time_player);
        //TIEMPO COMPLETO DE LA CANCION
        this.completeTime=(TextView) view.findViewById(R.id.end_time_player);
        //RECYCLER VIEW PARA LA LISTA DE MUSICA
        MusicPlayList musicList=new MusicPlayList(getContext());
        RecyclerView recyclerView= view.findViewById(R.id.recyclerview_list_music_player);
        int sizeListMusic= new MusicPlayList().getItemCount();
        recyclerView.setMinimumHeight(80*sizeListMusic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(musicList);

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


    @SuppressLint("DefaultLocale")
    public void loadSelectedMusic(){
        Song music=MusicPlayerControllerPresenter.musicPlayerControllerPresenter.getCurrentMusic();
        this.name_song.setText(music.getName());
        this.name_artist.setText(music.getArtist());
        mediaPlayer=MediaPlayer.create(getContext(),music.getMusic());
        this.timeLine.setMax((int) mediaPlayer.getDuration());
        this.handler.postDelayed(updateSongTime,100);
        this.endTime=mediaPlayer.getDuration();
        this.completeTime.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long)endTime),
                TimeUnit.MILLISECONDS.toSeconds((long)endTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)endTime)))
        );
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
      if(mediaPlayer!=null){
          this.mediaPlayer.stop();
      }
    }

    @Override
    public void random() {

    }

    @Override
    public void repeat(boolean state) {
        if(state){
            mediaPlayer.setLooping(false);
            this.repeatButton.setBackgroundResource(R.drawable.button_player_repeat_disabled_mp3);
        }
        else {
            mediaPlayer.setLooping(true);
            this.repeatButton.setBackgroundResource(R.drawable.button_player_repeat_enabled_mp3);
        }
    }

    @Override
    public void back() {

        if(mediaPlayer.isPlaying()){
            this.stop();
            loadSelectedMusic();
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();;
            loadSelectedMusic();
        }
    }

    @Override
    public void advance() {
        if(mediaPlayer.isPlaying()){
            this.stop();
            loadSelectedMusic();
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();;
            loadSelectedMusic();
        }
    }

    private Runnable updateSongTime = new Runnable(){
        @SuppressLint("DefaultLocale")
        public void run(){
            startTime=mediaPlayer.getCurrentPosition();
            currentTime.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long)startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long)startTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime)))
            );
            timeLine.setProgress((int)startTime);
            handler.postDelayed(this,100);
        }
    };

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