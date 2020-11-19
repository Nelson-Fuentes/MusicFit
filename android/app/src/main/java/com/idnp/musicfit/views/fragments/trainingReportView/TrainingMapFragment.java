package com.idnp.musicfit.views.fragments.trainingReportView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.idnp.musicfit.R;

import com.idnp.musicfit.views.toastManager.ToastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
//para la animación: https://www.youtube.com/watch?v=JLIFqqnSNmg
public class TrainingMapFragment extends Fragment {
    GoogleMap map;
    Boolean myPosition = true;
    JSONObject jso;
    Double longOri, latOri;
    ToastManager mytoast;



    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        @Override
        public void onMapReady(GoogleMap googleMap) {


            map = googleMap;
            //PARA LOS PERMISOS DE ACCESO
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)){
                }else{
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
                }
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)){
                }else{
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                return;
            }

            //PARA OBTENER MI LOCALIZACIÓN
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if(myPosition){
                        latOri=location.getLatitude();
                        longOri=location.getLongitude();
                        myPosition=false;

                        mytoast= new ToastManager(getContext());

                        mytoast.showToast("("+latOri+","+googleMap.getMapType()+")");
                        //para graficar esta posición en el mapa
                        LatLng myPosition = new LatLng(latOri, longOri);

                        googleMap.addMarker(new MarkerOptions().position(myPosition).title("Inicio"));
                        CameraPosition cameraPosition= new CameraPosition.Builder()
                                .target(new LatLng(latOri,longOri))
                                .zoom(15)
                                .bearing(30)
                                .build();
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        LatLng [] p ={
                                new LatLng(-16.4322583,-71.5642415),
                                new LatLng(-16.4322687,-71.5642517),
                                new LatLng(-16.4322791,-71.5642623),
                                new LatLng(-16.4322895,-71.5642727),
                                new LatLng(-16.4322999,-71.5642831),
                                new LatLng(-16.4323003,-71.5642935),

                        };
                       /*for(int i=0;i<p.length-1;i++){
                            map.addPolyline((new PolylineOptions()).add(p[i],p[i+1]).width(5).color(Color.CYAN));
                        }*/
                        map.addPolyline((new PolylineOptions()).add(myPosition,p[p.length-1]).width(5).color(Color.CYAN));


                        map.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
                        googleMap.addMarker(new MarkerOptions().position(p[p.length-1]).title("Final position"));


                    }
                }
            });

        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode,String perissions[],int[] grantResults){
        switch (requestCode){
            case 1:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                }else{
                }
                return;
            }
        }
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
}