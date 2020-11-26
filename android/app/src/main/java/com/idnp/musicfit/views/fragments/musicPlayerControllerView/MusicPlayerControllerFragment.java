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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.MusicPlayList;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.presenter.trainingReportPresenter.TrainingReportPresenter;
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
    private Song music;
    private int selectMusic;

    public MusicPlayerControllerFragment(){

    }
    public MusicPlayerControllerFragment(int selectMusic){
        this.selectMusic=selectMusic;

    }

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
        try {
            this.view =  inflater.inflate(R.layout.fragment_music_player_controller, container, false);
            // ... rest of body of onCreateView() ...
        } catch (Exception e) {
            Log.d("onCreateView", e.getMessage());
            throw e;
        }


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
        if (MusicPlayerControllerPresenter.musicPlayerControllerPresenter==null) {
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter = new MusicPlayerControllerPresenter(this);

        }
        if (this instanceof  MusicPlayerMiniControllerFragment){
            if (!isBigMusicPlayerController)
                MusicPlayerControllerPresenter.musicPlayerControllerPresenter.setView(this);
        } else {
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter.setView(this);
            play();
        }


    }


    @SuppressLint("DefaultLocale")
    public void loadSelectedMusic(){
        music=MusicPlayerControllerPresenter.musicPlayerControllerPresenter.getCurrentMusic();
        Log.d("MUSicaseleccionada","MUSICA SELECCIONADA "+music.getName());
        this.name_song.setText(music.getName());
        this.name_artist.setText(music.getArtist());
        mediaPlayer=MediaPlayer.create(getContext(),music.getMusic());
        this.timeLine.setMax((int) mediaPlayer.getDuration());
        this.handler.postDelayed(updateSongTime,100);
        this.endTime=mediaPlayer.getDuration();
        this.completeTime.setText(createTimerLabel(endTime));

        this.timeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                    timeLine.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //ORDEN DESPUES QUE TERMINE UNA CANCION RANDOM, REPEAT, NORMAL
        this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlay) {

            }
        });
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
            currentTime.setText(createTimerLabel(startTime));
            timeLine.setProgress((int)startTime);
            handler.postDelayed(this,100);
        }
    };

    public String createTimerLabel(int duration){
        String timerLabel="";
        int min=duration/1000/60;
        int sec=duration/1000%60;

        timerLabel+=min+":";
        if(sec<10) timerLabel+="0";
        timerLabel+=sec;

        return timerLabel;
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