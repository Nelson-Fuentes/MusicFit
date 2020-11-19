package com.idnp.musicfit.models.dbconector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectionStretchRouteDB extends SQLiteOpenHelper {

    final String TABLE_STRETCH_ROUTE="CREATE TABLE reports(latstart FLOAT, longstart FLOAT,latend FLOAT, longend FLOAT, idStretch INTEGER)";
    public ConnectionStretchRouteDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_STRETCH_ROUTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
