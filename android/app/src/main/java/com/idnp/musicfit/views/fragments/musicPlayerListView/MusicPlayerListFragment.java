package com.idnp.musicfit.views.fragments.musicPlayerListView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.adapters.MusicAdapter;
import com.idnp.musicfit.models.adapters.ReportAdapter;
import com.idnp.musicfit.models.entities.Song;
import com.idnp.musicfit.presenter.musicPlayerListPresenter.MusicPlayerListPresenter;
import com.idnp.musicfit.presenter.musicPlayerListPresenter.iMusicPlayerListPresenter;
import com.idnp.musicfit.presenter.trainingReportListPresenter.TrainingReportListPresenter;
import com.idnp.musicfit.presenter.trainingReportListPresenter.iTrainingReportListPresenter;
import com.idnp.musicfit.views.activities.mainView.MainActivity;
import com.idnp.musicfit.views.fragments.musicPlayerControllerView.MusicPlayerControllerFragment;

import java.util.ArrayList;


public class MusicPlayerListFragment extends Fragment implements iMusicPlayerListView {

    private View view;
    private MusicAdapter musicAdapter;
    private RecyclerView musicListView;
    private iMusicPlayerListPresenter musicPlayerListPresenter;
    public static final int REQUEST_CODE = 1;

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

    @Override
    public void onStart() {
        super.onStart();
        permission();
    }

    public void permission(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        }
        else
        {
            Toast.makeText(getContext(),"Permission Granted!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE)
        {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {

            }
            else{
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        }
    }

    private void loadComponents(View view){

        //vinculando el recyclerview
        this.musicListView = view.findViewById(R.id.music_list_recycler);
        LinearLayoutManager manager=new LinearLayoutManager(this.getContext());
        musicListView.setLayoutManager(manager);
        //crear el adaptador
        this.musicAdapter = new MusicAdapter(getContext());
        //agrega adaptador
        this.musicListView.setAdapter(this.musicAdapter);

        this.musicPlayerListPresenter = new MusicPlayerListPresenter(this);
        this.musicPlayerListPresenter.loadMusicList(getContext());
    }

    @Override
    public void showMusicList(ArrayList<Song> musicList) {
        this.musicAdapter.setDataSet(musicList);
    }
}