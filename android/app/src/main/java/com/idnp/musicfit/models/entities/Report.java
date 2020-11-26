package com.idnp.musicfit.models.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class Report {
    //fecha y hora en la que se creó este reporte de entrenamiento
    private int startDay;//--
    private int startMonth;//--
    private int startYear;//--
    private int startHour;//--
    private int startMin;//--
    private int startSec;//--

    private int durationHour;
    private int durationMin;
    private int durationSec;

    private boolean isEnd=false;//si se terminó el entrenamiento
    private LatLng startP;//--
    private LatLng endP;
    private String ID;//--
    private int KM;//--
    private int kcal;//--

    public Report(int startDay, int startMonth, int startYear, int startHour,int startMin,int startSec,LatLng startPosition) {
        this.startSec=startSec;
        this.startMin=startMin;
        this.startHour=startHour;
        this.startDay=startDay;
        this.startMonth=startMonth;
        this.startYear=startYear;
        this.startP=startPosition;
        this.ID=""+startSec+""+startMin+""+startHour+""+startDay+""+startMonth+""+startYear;

        this.durationHour=0;
        this.durationMin=0;
        this.durationSec=0;

        this.KM=0;
        this.kcal=0;

    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getStartSec() {
        return startSec;
    }

    public void setStartSec(int startSec) {
        this.startSec = startSec;
    }

    public int getDurationHour() {
        return durationHour;
    }

    public void setDurationHour(int durationHour) {
        this.durationHour = durationHour;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public LatLng getStartP() {
        return startP;
    }

    public void setStartP(LatLng startP) {
        this.startP = startP;
    }

    public LatLng getEndP() {
        return endP;
    }

    public void setEndP(LatLng endP) {
        this.endP = endP;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getKM() {
        return KM;
    }

    public void setKM(int KM) {
        this.KM = KM;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public void start(){

    }
    public void pause(){

    }
    public void stop(){

    }
}
