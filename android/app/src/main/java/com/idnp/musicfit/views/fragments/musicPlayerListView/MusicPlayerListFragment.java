package com.idnp.musicfit.views.fragments.musicPlayerListView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.adapters.MusicAdapter;
import com.idnp.musicfit.models.adapters.ReportAdapter;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.presenter.musicPlayerListPresenter.MusicPlayerListPresenter;
import com.idnp.musicfit.presenter.musicPlayerListPresenter.iMusicPlayerListPresenter;
import com.idnp.musicfit.presenter.trainingReportListPresenter.TrainingReportListPresenter;
import com.idnp.musicfit.presenter.trainingReportListPresenter.iTrainingReportListPresenter;

import java.util.ArrayList;


public class MusicPlayerListFragment extends Fragment implements iMusicPlayerListView {

    private View view;
    private MusicAdapter musicAdapter;
    private RecyclerView musicListView;
    private iMusicPlayerListPresenter musicPlayerListPresenter;

    public MusicPlayerListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view  = inflater.inflate(R.layout.fragment_music_list, container, false);
            this.loadComponents(this.view);
        }
        return  this.view;
    }

    private void loadComponents(View view){

        //vinculando el recyclerview
        this.musicListView = view.findViewById(R.id.music_list_recycler);
        LinearLayoutManager manager=new LinearLayoutManager(this.getContext());
        musicListView.setLayoutManager(manager);
        //crear el adaptador
        this.musicAdapter = new MusicAdapter();
        //agrega adaptador
        this.musicListView.setAdapter(this.musicAdapter);

        this.musicPlayerListPresenter = new MusicPlayerListPresenter(this);
        this.musicPlayerListPresenter.loadMusicList();
    }

    @Override
    public void showMusicList(ArrayList<Song> musicList) {
        this.musicAdapter.setDataSet(musicList);
    }
}