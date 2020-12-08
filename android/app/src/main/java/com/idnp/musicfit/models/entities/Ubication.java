package com.idnp.musicfit.models.entities;

import com.google.android.gms.maps.model.LatLng;

public class Ubication {

    private String idReport;
    private int orden;
    private LatLng ubicacion;
    private int isBreakPoint;
    public static final int BREAK_POINT=1;
    public static final int NONE_BREAK_POINT=0;


    public Ubication(String idReport, int orden, LatLng ubicacion,int isBreakPoint){
        this.idReport = idReport;
        this.orden = orden;
        this.ubicacion = ubicacion;
        this.isBreakPoint=isBreakPoint;
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

    public int isBreakPoint() {
        return isBreakPoint;
    }

    public void setBreakPoint(int breakPoint) {
        isBreakPoint = breakPoint;
    }
}
