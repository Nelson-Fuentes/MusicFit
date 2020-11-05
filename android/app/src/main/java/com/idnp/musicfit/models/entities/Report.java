package com.idnp.musicfit.models.entities;

import java.util.Date;

public class Report {
    //fecha y hora en la que se creó este reporte de entrenamiento
    private Date startTraining;
    //duración de entrenamiento
    private Date endTraining;
    //kilometros recorridos
    private double kmTraveled;
    //lugar de inicio de entrenamiento
    private String placeStart;
    //lugar de fin de entrenamiento
    private String placeEnd;
    //icono del reporte-- por el momento se usará uno genérico
    private int icon;

    public Report(Date startTraining, Date endTraining, double kmTraveled, String placeStart, String placeEnd,int icon) {
        this.startTraining = startTraining;
        this.endTraining = endTraining;
        this.kmTraveled = kmTraveled;
        this.placeStart = placeStart;
        this.placeEnd = placeEnd;
        this.icon=icon;
    }

    public Date getStartTraining() { return startTraining; }

    public void setStartTraining(Date startTraining) {
        this.startTraining = startTraining;
    }

    public Date getEndTraining() { return endTraining; }

    public void setEndTraining(Date endTraining) {
        this.endTraining = endTraining;
    }

    public double getKmTraveled() {
        return kmTraveled;
    }

    public void setKmTraveled(double kmTraveled) {
        this.kmTraveled = kmTraveled;
    }

    public String getPlaceStart() {
        return placeStart;
    }

    public void setPlaceStart(String placeStart) {
        this.placeStart = placeStart;
    }

    public String getPlaceEnd() {
        return placeEnd;
    }

    public void setPlaceEnd(String placeEnd) {
        this.placeEnd = placeEnd;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
