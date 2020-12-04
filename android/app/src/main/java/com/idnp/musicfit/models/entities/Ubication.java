package com.idnp.musicfit.models.entities;

import com.google.android.gms.maps.model.LatLng;

public class Ubication {

    private String idReport;
    private int orden;
    private LatLng ubicacion;

    public Ubication(String idReport, int orden, LatLng ubicacion){
        this.idReport = idReport;
        this.orden = orden;
        this.ubicacion = ubicacion;
    }

    public String getIdReport() {
        return idReport;
    }

    public void setIdReport(String idReport) {
        this.idReport = idReport;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String toString(){
        return "idReport: "+getIdReport()+" - orden: "+getOrden()+" - ubicacion: "+getUbicacion();
    }
}
