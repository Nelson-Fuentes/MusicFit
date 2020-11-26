package com.idnp.musicfit.models.entities;

import com.google.android.gms.maps.model.LatLng;

public class StretchRoute {

    private int idReport;
    private LatLng start;
    private LatLng end;

    public int getId() {
        return idReport;
    }

    public void setId(int id) {
        this.idReport = id;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }
}
