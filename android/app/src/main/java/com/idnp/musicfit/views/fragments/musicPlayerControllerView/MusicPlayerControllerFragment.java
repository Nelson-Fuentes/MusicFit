package com.idnp.musicfit.views.fragments.musicPlayerControllerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.idnp.musicfit.R;

import com.idnp.musicfit.models.entities.Song;

import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerNotificationReceiver;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerNotificationDataView;
import com.idnp.musicfit.models.services.trainingService.TrainingNotificationReceiver;
import com.idnp.musicfit.presenter.musicPlayerControllerPresenter.MusicPlayerControllerPresenter;
import com.idnp.musicfit.presenter.musicPlayerPresenter.MusicPlayerPresenter;
import com.idnp.musicfit.views.activities.mainView.MainActivity;


import static com.idnp.musicfit.models.services.trainingService.NotificationTraining.ACTION_PLAY;
import static com.idnp.musicfit.models.services.trainingService.NotificationTraining.CHANNEL_ID_2;

import static com.idnp.musicfit.presenter.musicPlayerPresenter.NotificationMusicPlayer.ACTION_NEXT;
import static com.idnp.musicfit.presenter.musicPlayerPresenter.NotificationMusicPlayer.ACTION_PREV;



public class MusicPlayerControllerFragment extends Fragment implements iMusicPlayerControllerView,ServiceConnection {

    public static final int STOPPED = -1;
    public static final int PAUSED = 0;
    public static final int PLAYED = 1;

    public View view;

    protected ImageButton randomButton,backButton,playButton,advanceButton,repeatButton;
    private ImageView imageSong;
    private TextView name_song,name_artist,currentTime,completeTime;
    private MusicPlayerControllerPresenter musicPlayerControllerPresenter;
    private MediaPlayer mediaPlayer;
    private SeekBar timeLine;
    private Handler handler=new Handler();
    private int startTime;
    private int endTime;
    private Song music;
    private int selectMusic;
    private MusicPlayerService musicPlayerService;
    private MusicPlayerPresenter musicPlayerPresenter;
    private MusicPlayerNotificationDataView playMusicNotificationDataView;
    private boolean isPlay=false;
    MediaSessionCompat mediaSession;
    public static MusicPlayerControllerFragment musicPlayerControllerFragment;

    public MusicPlayerControllerFragment(){

    }
    public MusicPlayerControllerFragment(int selectMusic){
        this.selectMusic=selectMusic;
        musicPlayerControllerFragment=this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mediaSession=new MediaSessionCompat(this.getContext(),"PlayAudio");

        return  this.view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MusicPlayerControllerPresenter.musicPlayerControllerPresenter=new MusicPlayerControllerPresenter(this,getContext());
        if(!MusicPlayerService.mediaPlayer.isPlaying()){
            MusicPlayerControllerPresenter.musicPlayerControllerPresenter.start();
        }

        MusicPlayerControllerPresenter.musicPlayerControllerPresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();


    }
    private byte[] getAlbumArt(String url){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(url.toString());
        byte [] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    @SuppressLint("DefaultLocale")
    public void loadSelectedMusic(){

        mediaPlayer=MusicPlayerService.mediaPlayer;
        music=MusicPlayerControllerPresenter.musicPlayerControllerPresenter.getCurrentMusic();
        Log.d("MUSicaseleccionada","MUSICA SELECCIONADA "+music.getName());
        this.name_song.setText(music.getName());
        this.name_artist.setText(music.getArtist());
        mediaPlayer=MediaPlayer.create(getContext(), Uri.parse(music.getMusic()));
        this.timeLine.setMax((int) mediaPlayer.getDuration());
        this.handler.postDelayed(updateSongTime,100);
        this.endTime=mediaPlayer.getDuration();
        this.completeTime.setText(createTimerLabel(endTime));
        byte [] imageAlbum=getAlbumArt(music.getMusic());
        if(imageAlbum!=null){
            Glide.with(this).asBitmap().load(imageAlbum).into(imageSong);
        }
        else{
            Glide.with(this).asBitmap().load(R.drawable.image_albun_practice).into(imageSong);
        }

        this.timeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    MusicPlayerService.mediaPlayer.seekTo(progress);
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
        this.playButton.setBackgroundResource(R.drawable.button_player_pause_mp3);
        showNotification(R.drawable.icon_player_pause_mp3);

    }

    @Override
    public void pause() {
        this.playButton.setBackgroundResource(R.drawable.button_player_play_mp3);
        showNotification(R.drawable.icon_player_play_mp3);
    }

    @Override
    public void stop() {
      if(mediaPlayer!=null){
          this.mediaPlayer.stop();
      }
    }

    @Override
    public void random(boolean state) {
        if(state){
            Toast.makeText(getContext(),"Modo Aleatorio Desactivado",Toast.LENGTH_SHORT).show();
            this.randomButton.setBackgroundResource(R.drawable.button_player_random_disabled_mp3);
        }
        else {
            Toast.makeText(getContext(),"Modo Aleatorio Activado",Toast.LENGTH_SHORT).show();
            this.randomButton.setBackgroundResource(R.drawable.button_player_random_enabled_mp3);
        }
    }

    @Override
    public void repeat(boolean state) {
        if(state){
            Toast.makeText(getContext(),"No Repetir Cancion",Toast.LENGTH_SHORT).show();
            mediaPlayer.setLooping(false);
            this.repeatButton.setBackgroundResource(R.drawable.button_player_repeat_disabled_mp3);
        }
        else {
            Toast.makeText(getContext(),"Repetir Cancion",Toast.LENGTH_SHORT).show();
            mediaPlayer.setLooping(true);
            this.repeatButton.setBackgroundResource(R.drawable.button_player_repeat_enabled_mp3);
        }
    }

    @Override
    public void back() {
        loadSelectedMusic();
        play();

    }

    @Override
    public void advance() {
        loadSelectedMusic();
        play();

    }

    private Runnable updateSongTime = new Runnable(){
        @SuppressLint("DefaultLocale")
        public void run(){
            startTime=MusicPlayerService.mediaPlayer.getCurrentPosition();
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
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MusicPlayerService.MusicPlayerIBinder playerIbinder = (MusicPlayerService.MusicPlayerIBinder)iBinder;
        musicPlayerService = playerIbinder.getServicio();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicPlayerService=null;
    }

    public void showNotification(int playPauseButton){

        Intent intent= new Intent(this.getContext(), MainActivity.class);
        PendingIntent contentIntent= PendingIntent.getActivity(this.getContext(),0,intent,0);

        //this.musicPlayerPresenter.updateDataNotificationMusicPlayer(this.isPlay);//actualizamos los datos a mostrar en la notificacion

        Intent playIntent= new Intent(this.getContext(), MusicPlayerNotificationReceiver.class).setAction(ACTION_PLAY);
        Intent nextIntent= new Intent(this.getContext(),MusicPlayerNotificationReceiver.class).setAction(ACTION_NEXT);
        Intent prevIntent= new Intent(this.getContext(),MusicPlayerNotificationReceiver.class).setAction(ACTION_PREV);
        PendingIntent playPendingIntent= PendingIntent.getBroadcast(this.getContext(),0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent nextPendingIntent= PendingIntent.getBroadcast(this.getContext(),0,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent prevPendingIntent= PendingIntent.getBroadcast(this.getContext(),0,prevIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Song music=MusicPlayerService.musicPlayerService.getCurrentMusic();
        byte [] album=getAlbumArt(music.getMusic());
        Bitmap picture;
        if(album!=null){
            picture=BitmapFactory.decodeByteArray(album,0,album.length);
        }
        else{
            picture= BitmapFactory.decodeResource(getResources(),R.drawable.image_albun_practice);
        }
        Notification musicPlayerNotification= new NotificationCompat.Builder(this.getContext(),CHANNEL_ID_2)
                .setSmallIcon(R.drawable.button_player_list_mp3)
                .setLargeIcon(picture)
                .setContentTitle(music.getName()+" - "+music.getArtist())
                .addAction(R.drawable.button_player_back_mp3,"Previous",prevPendingIntent)
                .addAction(playPauseButton,"Play",playPendingIntent)
                .addAction(R.drawable.button_player_advance_mp3,"Next",nextPendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(contentIntent)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager= (NotificationManager)
                this.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,musicPlayerNotification);

    }
    public void startDataNotificationMusicPlayer(MusicPlayerNotificationDataView stateTraining){
        this.playMusicNotificationDataView=stateTraining;
    }
    @Override
    public void updateDataNotificationMusicPlayer(String stateTrainingTitle,int backgroundTrainingState){
        this.playMusicNotificationDataView.setStatusTitle(stateTrainingTitle);
        this.playMusicNotificationDataView.setBackgroundStatus(backgroundTrainingState);
    }
}