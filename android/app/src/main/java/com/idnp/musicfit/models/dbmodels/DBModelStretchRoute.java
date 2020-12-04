package com.idnp.musicfit.models.dbmodels;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.idnp.musicfit.models.dbconector.ConnectionStretchRouteDB;
import com.idnp.musicfit.models.entities.Ubication;

public class DBModelStretchRoute {
    public SQLiteDatabase getConn(Context context){
        ConnectionStretchRouteDB conn= new ConnectionStretchRouteDB(context,"reports",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();
        return  db;
    }

    public int insertStretchRoute(Context context, Ubication sr){
        int res=0;
//        String sql="INSERT INTO reports (latstart,longstart,latend,longend,idStretch) VALUES ('"+sr.getStart().latitude+"','"+sr.getStart().longitude+"','"+sr.getEnd().latitude+"','"+sr.getEnd().longitude+"','"+sr.getId()+"')";
//        SQLiteDatabase db=this.getConn(context);
//        try {
//            db.execSQL(sql);
//            res=1;
//        }catch (Exception e){
//
//        }
        return res;
    }

}
