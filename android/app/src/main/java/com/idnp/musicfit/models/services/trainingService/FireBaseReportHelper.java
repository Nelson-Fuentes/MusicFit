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

    public ArrayList<Report> getSaveReportsTraining(String idUser, iTrainingReportListView trainingReportListView){
        ArrayList<Report> reports = new ArrayList<>();
        musicfitFireBase.getChild(FIREBASE_CHILD_REPORT_COLLECTION).child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reports.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Report u = dataSnapshot.getValue(Report.class);
                    Log.d("example reports", u.toString()+" <-- data");
                    reports.add(u);
                }
                trainingReportListView.showReportList(reports);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return reports;
    }

    public void saveLocationsTraining(String idReport, ArrayList<Ubication> locationsTraining){
        DatabaseReference childReference=musicfitFireBase.getChild(FIREBASE_CHILD_LOCATION_COLLECTION).child(idReport);
        for(Ubication location:locationsTraining){
            childReference.child(""+location.getOrden()).setValue(location);
        }
    }
    public ArrayList<Ubication> getSaveLocationTraining(String idReport){
        ArrayList<Ubication> ubications = new ArrayList<Ubication>();
        musicfitFireBase.getChild(FIREBASE_CHILD_LOCATION_COLLECTION).child(idReport).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ubications.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Ubication u = dataSnapshot.getValue(Ubication.class);
                    Log.d("example locations", u.toString()+" <-- data");
                    ubications.add(u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ubications;
    }

}
