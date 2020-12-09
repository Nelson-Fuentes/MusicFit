package com.idnp.musicfit.models.entities;

//import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

public class Ubication {

    private String id;
    private String idReport;
    private int orden;
    private LatLng position;
    private int isBreakPoint;
    public static final int BREAK_POINT=1;
    public static final int NONE_BREAK_POINT=0;


    public Ubication(){}
    public Ubication(String idReport, int orden, LatLng position, int isBreakPoint){
        this.idReport = idReport;
        this.orden = orden;
        this.position = position;
        this.isBreakPoint=isBreakPoint;
        this.id = UUID.randomUUID().toString();
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

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String toString(){
        return "idReport: "+getIdReport()+" - orden: "+getOrden()+" - ubicacion: "+ getPosition().toString();
    }

    public int isBreakPoint() {
        return isBreakPoint;
    }

    public void setBreakPoint(int breakPoint) {
        isBreakPoint = breakPoint;
    }
}
