package com.idnp.musicfit.models.adapters;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.models.services.musicPlayerService.MusicPlayerService;
import com.idnp.musicfit.presenter.musicPlayerControllerPresenter.MusicPlayerControllerPresenter;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.MusicPlayerControllerFragment;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;

import java.util.ArrayList;
import java.util.Date;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private ArrayList<Song> musicList;
    private Context context;

    public MusicAdapter(Context context) {
        this.musicList = new ArrayList<Song>();
        this.context=context;
    }

    public void setDataSet(ArrayList<Song> musicList){
        this.musicList=musicList;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MusicAdapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_player_mp3,parent,false);
        return new MusicAdapter.MusicHolder(view);
    }
    @Override
    public void onBindViewHolder( MusicAdapter.MusicHolder holder, int position) {
        Song music=musicList.get(position);
        holder.nameSong.setText(music.getName());
        holder.nameArtist.setText(music.getArtist());
        byte[] image= getAlbumArt(music.getMusic());
        if(image!=null){
            Glide.with(context).asBitmap()
                    .load(image)
                    .into(holder.iconMusic);
        }
        else{
            Glide.with(context)
                    .load(R.drawable.icon_music_playlist)
                    .into(holder.iconMusic);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicPlayerService.musicPlayerService.position=position;
                MusicPlayerControllerFragment musicPlayerControllerFragment=new MusicPlayerControllerFragment(position);
                if(MusicPlayerService.mediaPlayer.isPlaying()){
                    MusicPlayerService.mediaPlayer.stop();
                }
                FragmentManager.fragmentManager.changeFragment( musicPlayerControllerFragment);

            }
        });

    }
    private byte[] getAlbumArt(String url){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(url.toString());
        byte [] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public static  class MusicHolder extends  RecyclerView.ViewHolder{
        private TextView nameSong;
        private TextView nameArtist;
        private ImageView iconMusic;

        public MusicHolder(@NonNull View musicItemview){
            super(musicItemview);
            this.nameSong = (TextView) musicItemview.findViewById(R.id.name_music);
            this.nameArtist = (TextView) musicItemview.findViewById(R.id.artist_music);
            this.iconMusic = (ImageView) musicItemview.findViewById(R.id.icon_music);
        }

    }

}
