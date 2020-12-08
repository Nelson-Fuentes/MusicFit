package com.idnp.musicfit.models.services.trainingService;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Database name
    public static final String DB_NAME = "TRAINING";
    public static final int DB_VERSION = 1;

    //Database tables
    //public static final String TABLE1_NAME = "REPORT";
    public static final String TABLE1_NAME = "UBICATION";
    public static final String TABLE2_NAME = "REPORT";

    //Table columns
    public static final String TABLE1_COL_ID = "id";
    public static final String TABLE1_COL_IDREPORT = "idReport";
    public static final String TABLE1_COL_ORDEN = "orden";
    public static final String TABLE1_COL_UBICATIONLAT = "ubicationLat";
    public static final String TABLE1_COL_UBICATIONLON = "ubicationLon";
    public static final String TABLE1_COL_IS_BREAKPOINT = "isBreakPoint";


//    public static final String TABLE1_COL_ID = "id";
//    public static final String TABLE1_COL_NAME = "name";
//    public static final String TABLE1_COL_AGE = "age";

    public static final String TABLE2_COL_ID = "id";
    public static final String TABLE2_COL_START_DAY = "startDay";
    public static final String TABLE2_COL_START_MONTH = "startMonth";
    public static final String TABLE2_COL_START_YEAR = "startYear";
    public static final String TABLE2_COL_START_HOUR = "startHour";
    public static final String TABLE2_COL_START_MIN = "startMin";
    public static final String TABLE2_COL_START_SEC = "startSec";
    public static final String TABLE2_COL_DURATION_HOUR = "durationHour";
    public static final String TABLE2_COL_DURATION_MIN = "durationMin";
    public static final String TABLE2_COL_DURATION_SEC = "durationSec";
    public static final String TABLE2_COL_IS_END = "isEnd";
    public static final String TABLE2_COL_STARTPLAT = "startPLat";
    public static final String TABLE2_COL_STARTPLON = "startPLon";
    public static final String TABLE2_COL_ENDPLAT = "endPLat";
    public static final String TABLE2_COL_ENDPLON = "endPLon";
    public static final String TABLE2_COL_KM = "km";
    public static final String TABLE2_COL_KCAL = "kcal";

    //CREATING TABLES QUERY
//    private static final String CREATE_TABLE1 = "create table " + TABLE1_NAME + " (" + TABLE1_COL_ID +
//            " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE1_COL_NAME + " TEXT NOT NULL, " +TABLE1_COL_AGE+ " INTEGER);";

    private static final String CREATE_TABLE1 = "create table " + TABLE1_NAME + " (" +
            TABLE1_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE1_COL_IDREPORT + " TEXT NOT NULL, " +
            TABLE1_COL_ORDEN + " INTEGER NOT NULL, " +
            TABLE1_COL_UBICATIONLAT + " DOUBLE NOT NULL, " +
            TABLE1_COL_UBICATIONLON + " DOUBLE NOT NULL," +
            TABLE1_COL_IS_BREAKPOINT + " INTEGER NOT NULL" +
            ");";

    private static final String CREATE_TABLE2 = "create table " + TABLE2_NAME + " (" +
            TABLE2_COL_ID + " TEXT PRIMARY KEY, " +
            TABLE2_COL_START_DAY + " INTEGER NOT NULL, " +
            TABLE2_COL_START_MONTH + " INTEGER NOT NULL, " +
            TABLE2_COL_START_YEAR + " INTEGER NOT NULL, " +
            TABLE2_COL_START_HOUR + " INTEGER NOT NULL, " +
            TABLE2_COL_START_MIN + " INTEGER NOT NULL, " +
            TABLE2_COL_START_SEC + " INTEGER NOT NULL, " +
            TABLE2_COL_DURATION_HOUR + " INTEGER NOT NULL, " +
            TABLE2_COL_DURATION_MIN + " INTEGER NOT NULL, " +
            TABLE2_COL_DURATION_SEC + " INTEGER NOT NULL, " +
            TABLE2_COL_IS_END + " INTEGER NOT NULL, " +
            TABLE2_COL_STARTPLAT + " DOUBLE NOT NULL," +
            TABLE2_COL_STARTPLON + " DOUBLE NOT NULL," +
            TABLE2_COL_ENDPLAT + " DOUBLE NOT NULL, " +
            TABLE2_COL_ENDPLON + " DOUBLE NOT NULL, " +
            TABLE2_COL_KM + " FLOAT NOT NULL, " +
            TABLE2_COL_KCAL + " FLOAT NOT NULL" +
            ");";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }
}
