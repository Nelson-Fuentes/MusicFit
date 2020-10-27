package com.idnp.musicfit.models.services.musicPlayerService;

public class MusicPlayerService {

    public static MusicPlayerService musicPlayerService;
    public static final int STOPPED = -1;
    public static final int PAUSED = 0;
    public static final int PLAYED = 1;


    public MusicPlayerService(){
        this.state = STOPPED;
    }

    private int state;

    public void play(){
        this.state = PLAYED;
    }
    public void pause() {
        this.state = PAUSED;
    }
    public void stop() {
        this.state = STOPPED;
    }

    public int getState() {
        return this.state;
    }
}
