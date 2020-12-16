package com.idnp.musicfit.models.services.trainingService;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Ubication;
import com.idnp.musicfit.models.services.musicfitFirebase.MusicfitFireBase;
import com.idnp.musicfit.views.fragments.trainingReportListView.iTrainingReportListView;

import java.util.ArrayList;

public class FireBaseReportHelper {

    public static final String FIREBASE_CHILD_REPORT_COLLECTION="testreports";
    public static final String FIREBASE_CHILD_LOCATION_COLLECTION="testlocation";

    private MusicfitFireBase musicfitFireBase;
    public FireBaseReportHelper(){
        musicfitFireBase = new MusicfitFireBase();
    }
    public void saveReportTraining(String idUser, Report report){
        musicfitFireBase.getChild(FIREBASE_CHILD_REPORT_COLLECTION).child(idUser).child(report.getID()).setValue(report);
    }

    public void saveLocationsTraining(String idReport, ArrayList<Ubication> locationsTraining){
        DatabaseReference childReference=musicfitFireBase.getChild(FIREBASE_CHILD_LOCATION_COLLECTION).child(idReport);
        for(Ubication location:locationsTraining){
            childReference.child(""+location.getOrden()).setValue(location);
        }
    }
}
