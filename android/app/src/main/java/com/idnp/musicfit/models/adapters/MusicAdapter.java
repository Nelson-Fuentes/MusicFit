package com.idnp.musicfit.models.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    public MusicAdapter() {
        this.musicList = new ArrayList<Song>();
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

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public static  class MusicHolder extends  RecyclerView.ViewHolder{
        private TextView nameSong;
        private TextView nameArtist;

        public MusicHolder(@NonNull View musicItemview){
            super(musicItemview);
            this.nameSong = (TextView) musicItemview.findViewById(R.id.name_music);
            this.nameArtist = (TextView) musicItemview.findViewById(R.id.artist_music);
        }

    }

}
