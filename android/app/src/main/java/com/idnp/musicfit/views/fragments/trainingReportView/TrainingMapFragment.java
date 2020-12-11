package com.idnp.musicfit.views.fragments.trainingReportView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;
import com.idnp.musicfit.R;

import com.idnp.musicfit.models.entities.LatLng;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.models.entities.Ubication;
import com.idnp.musicfit.models.services.trainingService.DBManager;
import com.idnp.musicfit.models.services.trainingService.ReportHelper;
import com.idnp.musicfit.models.services.trainingService.TrainingHelper;
import com.idnp.musicfit.models.services.trainingService.TrainingServiceConstants;
import com.idnp.musicfit.views.toastManager.ToastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//para la animación: https://www.youtube.com/watch?v=JLIFqqnSNmg
//smooth animation: https://www.youtube.com/watch?v=7amkdxHVGz4
public class TrainingMapFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private GoogleMap map;//guardará la referencia al Map
    private LatLng myPos;// esta variable guardará siempre mi pocisión
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    private final int START_MARKER=1;
    private final int END_MARKER=2;
    private boolean training=false;
    Marker userLocationMarker;
    private ArrayList<Polyline> polylines;
    private Marker marker_start;
    private Marker marker_end;

    private Report myReport;

    public TrainingMapFragment(Report report){
        myReport=report;
        polylines= new ArrayList<>();
    }


    //------------------------para habilitar la localización de usuario
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }
    private void zoomToUserLocation() {//---------------------ok
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //changing here
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(myPos.latitude,myPos.longitude), 20));
    }
    private void zoomToAnyLocation(LatLng location) {//---------------------ok
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //changing here
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(location.latitude,location.longitude), 20));
    }

    private void drawPolyline(double lat, double lng){//----------------------ok
        //changing here
        polylines.add(map.addPolyline((new PolylineOptions()).add(new com.google.android.gms.maps.model.LatLng(myPos.latitude,myPos.longitude),new com.google.android.gms.maps.model.LatLng(lat,lng)).width(8).color(Color.CYAN)));
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        //------------ cuando el mapa esté listo
        @Override
        public void onMapReady(GoogleMap googleMap) {

            map = googleMap;//guarda el objeto mapa en una variable propia nuestra
            myPos=null;
            //PARA LOS PERMISOS DE ACCESO
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                enableUserLocation();
//                zoomToUserLocation();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //SE MUESTRA EL TEXTO DE DIALOGO PORQUE ESTE PERMISO ES NECESARIO
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
                }
            }

            loadMapTrainingProcess(getContext());

        }
    };
    //------------------ método para mover el marker junto con el cambio de posición de usuario
    private void setUserLocationMarker(LatLng latLng){

        if(userLocationMarker==null){// si es nulo creamo un nuevo marcador
            MarkerOptions markerOptions= new MarkerOptions();
            //changing here
            markerOptions.position(new com.google.android.gms.maps.model.LatLng(latLng.latitude,latLng.longitude));//para actualizar la posición
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.usermarker));//para agregar el icono al marker
            markerOptions.anchor((float)0.5,(float)0.5);
            userLocationMarker= map.addMarker(markerOptions);
            //changing here
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(latLng.latitude,latLng.longitude),17));
        }else{//quiere decir que ya se creó previamente
            //changing here
            userLocationMarker.setPosition(new com.google.android.gms.maps.model.LatLng(latLng.latitude,latLng.longitude));//para actualizar la posición
            //changing here
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new com.google.android.gms.maps.model.LatLng(latLng.latitude,latLng.longitude),17));//para posicionar la cámara en el usuario
        }
    }

    private void addCustomMarker(LatLng latLng, int marker,String title,int type_marker){
        MarkerOptions markerOptions= new MarkerOptions();
        //changing here
        markerOptions.position(new com.google.android.gms.maps.model.LatLng(latLng.latitude,latLng.longitude));//para actualizar la posición
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(marker));//para agregar el icono al marker
        markerOptions.anchor((float)0.5,(float)0.5);
        if(type_marker==START_MARKER) {
            marker_start = map.addMarker(markerOptions);
            marker_start.showInfoWindow();
        }else{
            marker_end = map.addMarker(markerOptions);
            marker_end.showInfoWindow();
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onStop(){
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if(key.equals(TrainingHelper.KEY_IS_TRAINING_SHARED)){
                    training=TrainingHelper.getLocationRequestStatus(getContext());
                }
                else if(key.equals(TrainingHelper.KEY_LAST_LOCATION_SHARED)){
                    String position= TrainingHelper.getSavedLastLocationUpdateShared(getContext());
                    if((!position.equals(TrainingHelper.NONE_LAST_LOCATION))){

                        String [] pos=position.split("/");
                        double lat= Double.parseDouble(pos[0]);
                        double lng= Double.parseDouble(pos[1]);
                        if(myPos!=null && training){
                            drawPolyline(lat,lng);
                        }
                        myPos=new LatLng(lat,lng);
                        zoomToUserLocation();
                        setUserLocationMarker(myPos);

                    }
                }else if(key.equals(ReportHelper.KEY_START_POSITION_TRAINING_SHARED)){
                    String position=ReportHelper.getStartPositionTrainingShared(getContext());
                    if(!position.equals(ReportHelper.NONE_START_POSITION_TRAINING)){
                        String [] pos=position.split("/");
                        double lat= Double.parseDouble(pos[0]);
                        double lng= Double.parseDouble(pos[1]);
                        addCustomMarker(new LatLng(lat,lng),R.drawable.icon_start_training,"start Training",START_MARKER);
                    }
                }
                else if(key.equals(ReportHelper.KEY_FINAL_POSITION_TRAINING_SHARED)){
                    String position=ReportHelper.getFinalPositionTrainingShared(getContext());
                    if(!position.equals(ReportHelper.NONE_FINAL_POSITION_TRAINING)){
                        String [] pos=position.split("/");
                        double lat= Double.parseDouble(pos[0]);
                        double lng= Double.parseDouble(pos[1]);
                        addCustomMarker(new LatLng(lat,lng),R.drawable.icon_end_training,"End Training",END_MARKER);
                    }
                }else if(key.equals(ReportHelper.START_ID_TRAINING_SHARED)){
                    if(ReportHelper.getStartIdTrainingShared(getContext()).equals(ReportHelper.NONE_START_ID)){
                        if(marker_start!=null)
                            marker_start.remove();
                        if(marker_end!=null)
                            marker_end.remove();
                        for(Polyline lines:polylines){
                            lines.remove();
                        }
                        polylines.clear();
                    }
                }

    }
    private void loadMapTrainingProcess(Context context){
                    String key= ReportHelper.getStartIdTrainingShared(context);
                    if(!key.equals(ReportHelper.NONE_START_ID)){

                        ArrayList<Ubication> locations=TrainingHelper.getLocationsReport(context,key);
                        if(locations.size()>0) {
                            zoomToAnyLocation(locations.get(0).getPosition());
                            addCustomMarker(locations.get(0).getPosition(), R.drawable.icon_start_training, "Start Training",START_MARKER);
                        }
                        ToastManager.toastManager.showToast(""+locations.size());
                        for(int i=1;i<locations.size();i++){
                            map.addPolyline((new PolylineOptions())
                                    .add(
                                            //CHANGING HERE
                                            new com.google.android.gms.maps.model.LatLng(locations.get(i-1).getPosition().latitude,locations.get(i-1).getPosition().longitude),
                                            new com.google.android.gms.maps.model.LatLng(locations.get(i).getPosition().latitude,locations.get(i).getPosition().longitude)

//                                            locations.get(i-1).getPosition(),
//                                            locations.get(i).getPosition()
                                    ).width(8).color(Color.CYAN));
                        }
                    }
    }
    private void loadMapTrainingReport(Context context){
        if(myReport!=null){
            //--------------- esto es para jalar datos de la nube de las ubicaciones de un reporte en específico
        }
    }




}