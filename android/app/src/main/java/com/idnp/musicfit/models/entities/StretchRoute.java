package com.idnp.musicfit.models.entities;

import com.google.android.gms.maps.model.LatLng;

public class StretchRoute {

    private int id;
    private LatLng start;
    private LatLng end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
