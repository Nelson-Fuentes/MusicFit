package com.idnp.musicfit.views.fragments.trainingReportView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;
import com.idnp.musicfit.R;

import com.idnp.musicfit.views.toastManager.ToastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

//para la animación: https://www.youtube.com/watch?v=JLIFqqnSNmg
//smooth animation: https://www.youtube.com/watch?v=7amkdxHVGz4
public class TrainingMapFragment extends Fragment {
    private GoogleMap map;//guardará la referencia al Map
    private LatLng myPos;// esta variable guardará siempre mi pocisión

    private int ACCESS_LOCATION_REQUEST_CODE = 10001;// para guardar si se tiene permiso para acceder a la posición de usuario
    FusedLocationProviderClient fusedLocationProviderClient;//para poder obtener la localización de un usuario
    LocationRequest locationRequest;
    Marker userLocationMarker;
    Circle userLocationAccuracyCircle;

    //------------------------para habilitar la localización de usuario
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }

    //-------------- obtiene la localización de usuario y hace zoom de la cámara
    private void zoomToUserLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> tastlocation = fusedLocationProviderClient.getLastLocation();
        tastlocation.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            }
        });

    }

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

        //------------ cuando el mapa esté listo
        @Override
        public void onMapReady(GoogleMap googleMap) {

            map = googleMap;//guarda el objeto mapa en una variable propia nuestra

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

//            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//                @Override
//                public void onMyLocationChange(Location location) {
//                    if(myPosition){
//                        latOri=location.getLatitude();//--obtendo mi laitud
//                        longOri=location.getLongitude();//obtengo mi longitud
//                        myPosition=false;//---------------------------
//                        myPos = new LatLng(latOri, longOri);//crea un objeto posición con mi locación
//                        mytoast= new ToastManager(getContext());//uso del gestor de Toast creado por nelson
//                        mytoast.showToast("("+latOri+","+longOri+")");//mostrar mi mensaje
//                        //para agregar un marcador en mi posición
//                        MarkerOptions markerOptions= new MarkerOptions().position(myPos).title("My position").snippet("i am alone");
//                        map.addMarker(markerOptions);
//                        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(myPos,16);//animación a mi posición
//                        map.animateCamera(cameraUpdate);
//
//
//
//                        /*googleMap.addMarker(new MarkerOptions().position(myPosition).title("Inicio"));
//                        CameraPosition cameraPosition= new CameraPosition.Builder()
//                                .target(new LatLng(latOri,longOri))
//                                .zoom(15)
//                                .bearing(30)
//                                .build();
//                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//

//
//                        //map.addPolyline((new PolylineOptions()).add(myPosition,p[p.length-1]).width(5).color(Color.CYAN));
//
//
//                        map.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
//                        googleMap.addMarker(new MarkerOptions().position(p[p.length-1]).title("Final position"));
//                    */
//
//                    }
//                }
//            });


        }
    };
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(map!=null){//hay que comprobar que el mapa se haya creado
                setUserLocationMarker(locationResult.getLastLocation());//le pasamos la última localización para mover el marker
            }
        }
    };

    //------------------ método para mover el marker junto con el cambio de posición de usuario
    private void setUserLocationMarker(Location location){
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        if(userLocationMarker==null){// si es nulo creamo un nuevo marcador
            MarkerOptions markerOptions= new MarkerOptions();
            markerOptions.position(latLng);//para actualizar la posición
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.usermarker));//para agregar el icono al marker
            markerOptions.rotation(location.getBearing());//para que la imagen rote junto con la dirección de la persona
            markerOptions.anchor((float)0.5,(float)0.5);
            userLocationMarker= map.addMarker(markerOptions);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
            ToastManager.toastManager.showToast("se creó el marcador");
        }else{//quiere decir que ya se creó previamente
            userLocationMarker.setPosition(latLng);//para actualizar la posición
            userLocationMarker.setRotation(location.getBearing());//para que la imagen rote junto con la dirección de la persona
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));//para posicionar la cámara en el usuario
            ToastManager.toastManager.showToast(latLng.latitude+","+latLng.longitude);
        }

    }
    //para obtener las actualizaciones de posiciones de usuario
    private void startLocationUpdates() {//habilitamos la actualización de usuario position
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }
    private void stopLocationUpdates(){//inhabilitamos la actualización de usuario position
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    @Override
    public void onStart(){
        super.onStart();
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            startLocationUpdates();
        }else{//si no hay permisos es necesario pedir permisos

        }
    }
    @Override
    public void onStop(){
        super.onStop();
        stopLocationUpdates();
    }




    //PARA SABER CUANDO SE CAMBIO LOS PERMISOS
    @Override
    public void onRequestPermissionsResult(int requestCode,String perissions[],int[] grantResults){

        if(requestCode==ACCESS_LOCATION_REQUEST_CODE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                enableUserLocation();
                zoomToUserLocation();
            }else{
                //PODEMOS MOSTRAR UN MENSAJE DE DIALO DE QUE ESTOS PERMISOS NO SON GRANTED
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

        //para habilitar la actualización del la localización de usuario
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity());//se puede crear fuera del mapa
        //animar el movimiento en el mápa
        locationRequest=LocationRequest.create();//iniciamos el objeto que se usará para la consulta de usuario
        locationRequest.setInterval(500);//le damos un intervalo de tiempo para que verifique el cambio
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//-------le damos la prioridad a la consulta de localización
    }
}