package com.idnp.musicfit.models.services.trainingService;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Ubication;

import java.util.ArrayList;

public class DBManager {
    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    private Report auxReport;
    private Ubication auxUbication;

    public DBManager(Context c){
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void insertUbication(Ubication u){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.TABLE1_COL_IDREPORT,u.getIdReport());
        contentValues.put(DataBaseHelper.TABLE1_COL_ORDEN,u.getOrden());
        contentValues.put(DataBaseHelper.TABLE1_COL_UBICATIONLAT,u.getUbicacion().latitude);
        contentValues.put(DataBaseHelper.TABLE1_COL_UBICATIONLON,u.getUbicacion().longitude);
        contentValues.put(DataBaseHelper.TABLE1_COL_IS_BREAKPOINT,u.isBreakPoint());
        database.insert(DataBaseHelper.TABLE1_NAME,null, contentValues);

    }
    public void insertReport(Report r){
//        ContentValues contentValue = new ContentValues();
//        contentValue.put(DataBaseHelper.TABLE1_COL_NAME,name);
//        contentValue.put(DataBaseHelper.TABLE1_COL_AGE,age);
//        database.insert(DataBaseHelper.TABLE1_NAME,null,contentValue);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.TABLE2_COL_ID,r.getID());
        contentValues.put(DataBaseHelper.TABLE2_COL_START_DAY,r.getStartDay());
        contentValues.put(DataBaseHelper.TABLE2_COL_START_MONTH,r.getStartMonth());
        contentValues.put(DataBaseHelper.TABLE2_COL_START_YEAR,r.getStartYear());
        contentValues.put(DataBaseHelper.TABLE2_COL_START_HOUR,r.getStartHour());
        contentValues.put(DataBaseHelper.TABLE2_COL_START_MIN,r.getStartMin());
        contentValues.put(DataBaseHelper.TABLE2_COL_START_SEC,r.getStartSec());

        contentValues.put(DataBaseHelper.TABLE2_COL_DURATION_HOUR,r.getStartHour());
        contentValues.put(DataBaseHelper.TABLE2_COL_DURATION_MIN,r.getStartMin());
        contentValues.put(DataBaseHelper.TABLE2_COL_DURATION_SEC,r.getStartSec());

        contentValues.put(DataBaseHelper.TABLE2_COL_IS_END,r.isEnd());
        contentValues.put(DataBaseHelper.TABLE2_COL_STARTPLAT, r.getStartP().latitude);
        contentValues.put(DataBaseHelper.TABLE2_COL_STARTPLON, r.getStartP().longitude);
        contentValues.put(DataBaseHelper.TABLE2_COL_ENDPLAT, r.getEndP().latitude);
        contentValues.put(DataBaseHelper.TABLE2_COL_ENDPLON, r.getEndP().longitude);
        contentValues.put(DataBaseHelper.TABLE2_COL_KM,r.getKM());
        contentValues.put(DataBaseHelper.TABLE2_COL_KCAL,r.getKcal());

        database.insert(DataBaseHelper.TABLE2_NAME,null,contentValues);

    }
//    public ArrayList getElements(){
//        database = dbHelper.getReadableDatabase();
//        ArrayList<String> arrayList = new ArrayList<String>();
//        Cursor cursor = database.rawQuery("SELECT * FROM "+DataBaseHelper.TABLE1_NAME,null);
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()){
//            arrayList.add("ID: "+cursor.getLong(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_ID))+" NAME: "+cursor.getString(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_NAME)) +" AGE:" +cursor.getString(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_AGE)) );
//            cursor.moveToNext();
//        }
//        return arrayList;
//    }

    // getUbicaciones para reporte en especifico --
    public ArrayList getUbications(String idReport){
        database = dbHelper.getReadableDatabase();
        ArrayList<Ubication> ubications = new ArrayList<Ubication>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE1_NAME+" WHERE "+ DataBaseHelper.TABLE1_COL_IDREPORT + " = " + idReport,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            auxUbication = new Ubication(
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_IDREPORT)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_ORDEN)),
                    new LatLng(
                            cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_UBICATIONLAT)),
                            cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_UBICATIONLON))
                    ),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_IS_BREAKPOINT))
            );
            ubications.add(auxUbication);
            cursor.moveToNext();

        }
        return ubications;
        
    }
    public ArrayList getReports(){
        database = dbHelper.getReadableDatabase();
        ArrayList<Report> reports= new ArrayList<Report>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE2_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            auxReport = new Report(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_START_DAY)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_START_MONTH)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_START_YEAR)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_START_HOUR)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_START_MIN)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_START_SEC)),
                    new LatLng(cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_STARTPLAT)),
                            cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_STARTPLON)))
            );
            auxReport.setID(cursor.getString(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_ID )));
            auxReport.setEndP(new LatLng(cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_ENDPLAT)),
                    cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_ENDPLON))));
            auxReport.setDurationHour(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_DURATION_HOUR)));
            auxReport.setDurationMin(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_DURATION_MIN)));
            auxReport.setDurationSec(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_DURATION_SEC)));

            auxReport.setEnd(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_IS_END)));
            auxReport.setKM(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_KM)));
            auxReport.setKcal(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.TABLE2_COL_KCAL)));

            reports.add(auxReport);
            cursor.moveToNext();
        }
        return reports;
    }

//    public Cursor fetch(){
//        String [] columns = new String [] { DataBaseHelper.TABLE1_COL_ID,
//                DataBaseHelper.TABLE1_COL_NAME,
//                DataBaseHelper.TABLE1_COL_AGE};
//        Cursor cursor = database.query(DataBaseHelper.TABLE1_NAME, columns, null, null, null,null,null);
//        if (cursor != null){
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
    public Cursor fetchTable1(){
        String [] columns = new String []{
          DataBaseHelper.TABLE1_COL_ID,
          DataBaseHelper.TABLE1_COL_IDREPORT,
          DataBaseHelper.TABLE1_COL_ORDEN,
          DataBaseHelper.TABLE1_COL_UBICATIONLAT,
          DataBaseHelper.TABLE1_COL_UBICATIONLON,
          DataBaseHelper.TABLE1_COL_IS_BREAKPOINT
        };
        Cursor cursor = database.query(DataBaseHelper.TABLE1_NAME,columns,null,null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchTable2(){
        String [] columns = new String [] {
                DataBaseHelper.TABLE2_COL_ID,
                DataBaseHelper.TABLE2_COL_START_DAY,
                DataBaseHelper.TABLE2_COL_START_MONTH,
                DataBaseHelper.TABLE2_COL_START_YEAR,
                DataBaseHelper.TABLE2_COL_START_HOUR,
                DataBaseHelper.TABLE2_COL_START_MIN,
                DataBaseHelper.TABLE2_COL_START_SEC,
                DataBaseHelper.TABLE2_COL_DURATION_HOUR,
                DataBaseHelper.TABLE2_COL_DURATION_MIN,
                DataBaseHelper.TABLE2_COL_DURATION_SEC,
                DataBaseHelper.TABLE2_COL_IS_END,
                DataBaseHelper.TABLE2_COL_STARTPLAT,
                DataBaseHelper.TABLE2_COL_STARTPLON,
                DataBaseHelper.TABLE2_COL_ENDPLAT,
                DataBaseHelper.TABLE2_COL_ENDPLON,
                DataBaseHelper.TABLE2_COL_KM,
                DataBaseHelper.TABLE2_COL_KCAL
        };
        Cursor cursor = database.query(DataBaseHelper.TABLE2_NAME,columns,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
// not needed
//    public int update (long id, String name, int age){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DataBaseHelper.TABLE1_COL_NAME,name);
//        contentValues.put(DataBaseHelper.TABLE1_COL_AGE,age);
//        int i = database.update(DataBaseHelper.TABLE1_NAME, contentValues,
//                DataBaseHelper.TABLE1_COL_ID + " = " + id, null );
//        return i;
//    }

    //borrar todas las ubicaciones con el idReporte
    public void deleteUbications(String idReport){
        database.delete(DataBaseHelper.TABLE1_NAME, DataBaseHelper.TABLE1_COL_IDREPORT + " = " + idReport, null);
    }
    public void deleteReport (String id){
        database.delete(DataBaseHelper.TABLE2_NAME, DataBaseHelper.TABLE2_COL_ID + " = " + id, null);
    }
    public void deleteDB(){
        context.deleteDatabase((DataBaseHelper.DB_NAME));
    }
    public void deleteTable(String name){
       // name = DataBaseHelper.TABLE1_NAME;
        name = DataBaseHelper.TABLE2_NAME;
        database.execSQL("DROP TABLE IF EXISTS " + name);
    }
}
