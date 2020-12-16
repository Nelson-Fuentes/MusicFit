package com.idnp.musicfit.models.entities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.idnp.musicfit.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayList {

    public static MusicPlayList musicPlayList;
    public static ArrayList<Song> musicList=new ArrayList<Song>();
    private Context context;


    public MusicPlayList(){
        if(musicList.isEmpty()){
            loadMusicPlayList();
        }
    }

    public MusicPlayList(Context context){
        this.context=context;
        if(musicList.isEmpty()){
            loadMusicPlayList();
        }
    }

    public void loadMusicPlayList(){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(uri,projection,
                null,null,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                String album=cursor.getString(0);
                String title=cursor.getString(1);
                String duration=cursor.getString(2);
                String path=cursor.getString(3);
                String artist=cursor.getString(4);
                Song song=new Song(path,title,artist,album,duration);
                musicList.add(song);

            }
        }
    }

    public ArrayList<Song> getMusicPlayList(){
        return musicList;
    }
}
