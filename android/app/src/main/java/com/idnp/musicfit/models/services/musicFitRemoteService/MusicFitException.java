package com.idnp.musicfit.models.services.musicFitRemoteService;

public  class MusicFitException extends Exception{

    private int string_code;
    public  MusicFitException(String string){
        super(string);
    }

    public MusicFitException(int string_code){
        super((String) null);
        this.string_code = string_code;
    }

    public int getStringCode(){
        return this.string_code;
    }

}
