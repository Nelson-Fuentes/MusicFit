package com.idnp.musicfit.views.fragments.trainingControllerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

//import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.LatLng;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Ubication;
import com.idnp.musicfit.models.services.musicfitFirebase.MusicfitFireBase;
import com.idnp.musicfit.models.services.trainingService.DBManager;
import com.idnp.musicfit.models.services.trainingService.ReportHelper;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingControllerPresenter.iTrainingControllerPresenter;
import com.idnp.musicfit.views.fragments.trainingReportView.TrainingReportFragment;

import java.util.ArrayList;
import java.util.UUID;


public class TrainingControllerFragment extends Fragment implements iTrainingControllerView {

    private View view;
    private iTrainingControllerPresenter trainingControllerPresenter;
    private ImageView play_button;
    private ImageView pause_button;
    private ImageView stop_button;
    private ImageView map_button;
    private Chronometer chronometer;
    private long pauseOffSet;
    private TextView lbl_play;
    private TextView lbl_pause;
    private TextView lbl_stop;
    private TextView lbl_map;
    private ImageView image_runner;

    private Button delete_button, update_button;
    private Button ubi_add_button, ubi_show_button, ubi_delete_button;
    private Button rep_add_button, rep_show_button, rep_del_button;

    /*private DBModelStretchRoute modelDBRouteStretch;
    private boolean controllAsync;*/

    private TextView lbl_no_resultados,lbl_resultados, number_km, lbl_km, number_pasos,lbl_pasos,number_cals,lbl_cals;
    private final long delay_buttons = 0;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private boolean running = false;
    private long time_gone = 0;

//    public void executeAsync(){
//        AsyncSaveStretchRoute assr=new AsyncSaveStretchRoute();
//        assr.execute();
//    }
//    public class AsyncSaveStretchRoute extends AsyncTask<Void,Integer, Boolean>//Clase para guardar stretchRoutes de manera async
//    {
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//        @Override
//        protected  void onPostExecute(Boolean aBoolean){
//            boolean ret=true;
//            if(controllAsync){
//                executeAsync();
//                ToastManager.toastManager.showToast("guardando tramos de rutas");
//            }
//        }
//    }

    public TrainingControllerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view = inflater.inflate(R.layout.fragment_training_controller, container, false);
        }

        stop_button = view.findViewById(R.id.stop_button_training);
        stop_button.setVisibility(View.VISIBLE);
        map_button = (ImageView)view.findViewById(R.id.map_button);
        map_button.setVisibility(View.VISIBLE);
        play_button = view.findViewById(R.id.play_button_training);
        delete_button = view.findViewById(R.id.delete_button);
        update_button = view.findViewById(R.id.update_button);


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("example4","deleting data");
                DBManager db = new DBManager(getActivity());
                db.open();
                //db.delete(1);
                db.close();

            }
        });
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("example5","updating data");
                DBManager db = new DBManager(getActivity());
                db.open();
              //  db.update(2,"painless death",10);
                db.close();
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("example3", "erasing alone");
                DBManager db = new DBManager(getActivity());
                db.open();
                db.deleteDB();
                db.close();
            }
        });

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("example2","showing data");
                DBManager db = new DBManager(getActivity());
                db.open();
                ArrayList<Report> reports = db.getReports();
                for(int i = 0;i<reports.size();i++){
                    Log.d("example2",i+" <-- la iteración");
                    Log.d("example2",reports.get(i).toString());
                }
               // ArrayList<String> aux = db.getElements();
               // Log.d("example2",aux+"<- data");
                db.close();



            }
        });
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("example","adding data");
                DBManager db = new DBManager(getActivity());
                db.open();
                Report auxReport = new Report(1,6,3,4,6,10,new LatLng(1,4));
                auxReport.setDurationHour(4);
                auxReport.setDurationMin(6);
                auxReport.setDurationSec(5);
                auxReport.setEnd(1);
                auxReport.setEndP(new LatLng(1,5));
                auxReport.setKM(19);
                auxReport.setKcal(391);
                db.insertReport(auxReport);
                //  db.insert("la prueba",30);
                db.close();

            }
        });

        ubi_add_button = view.findViewById(R.id.ubi_add_button);
        ubi_show_button = view.findViewById(R.id.ubi_show_button);
        ubi_delete_button = view.findViewById(R.id.ubi_delete_button);

        ubi_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("exampleUbi", "adding ubicacion");
                DBManager db = new DBManager(getActivity());
                db.open();
                Ubication auxUbication = new Ubication("112111",3,new LatLng(5,3),Ubication.NONE_BREAK_POINT);
                db.insertUbication(auxUbication);
                db.close();
                Log.d("exampleUbi", "ending adding ubicacion");
            }
        });
        ubi_show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("exampleUbi2", "showing ubicaciones");
                DBManager db = new DBManager(getActivity());
                db.open();
                ArrayList<Ubication>ubicaciones= db.getUbications("112111");
                for(int i=0; i<ubicaciones.size();i++){
                    Log.d("exampleUbi2", ubicaciones.get(i).toString());
                }
                db.close();
                Log.d("exampleUbi2", "ending showing - > "+ ubicaciones.size());
            }
        });
        ubi_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=ReportHelper.getStartIdTrainingShared(getContext());
                Log.d("exampleUbi3", "deleting ubicacion"+id);
                DBManager db = new DBManager(getActivity());
                db.open();

                db.deleteUbications(ReportHelper.refactorId(id));
                db.close();

            }
        });

        rep_add_button = view.findViewById(R.id.report_add);
        rep_show_button = view.findViewById(R.id.report_show);
        rep_del_button = view.findViewById(R.id.report_delete);

        rep_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("exampleRepFire1", "adding report");
               //report auxiliar
                Report auxReport = new Report(1,6,3,4,6,10,new LatLng(1,4));
                auxReport.setDurationHour(4);
                auxReport.setDurationMin(6);
                auxReport.setDurationSec(5);
                auxReport.setEnd(1);
                auxReport.setEndP(new LatLng(1,12.312314));
                auxReport.setKM(19);
                auxReport.setKcal(391);

                //instancia de la base de datos firebase
                MusicfitFireBase musicfitFireBase = new MusicfitFireBase();

                //generando claves aleatorias
                String aleatorio = UUID.randomUUID().toString();

                //obteniendo referencias especificas
                musicfitFireBase.getChild("reports").child(aleatorio).setValue(auxReport);
                String aleatorio2 = UUID.randomUUID().toString();
                String aleatorio3 = UUID.randomUUID().toString();
                Ubication auxUbication = new Ubication("112111",3,new LatLng(5,3),Ubication.NONE_BREAK_POINT);
                Ubication auxUbication2 = new Ubication("112111",5,new LatLng(15,4),Ubication.NONE_BREAK_POINT);
                musicfitFireBase.getChild("reports").child(aleatorio).child("ubications").child(aleatorio2).setValue(auxUbication);
                musicfitFireBase.getChild("reports").child(aleatorio).child("ubications").child(aleatorio3).setValue(auxUbication2);

            }
        });

        rep_show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Ubication> ubications = new ArrayList<Ubication>();
                Log.d("exampleRepFire2", "showing ubications");
                MusicfitFireBase musicfitFireBase = new MusicfitFireBase();
                musicfitFireBase.getChild("ubications").child("idReport-clave-unica").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ubications.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Ubication u = dataSnapshot.getValue(Ubication.class);
                            Log.d("exampleRepFire2", u.toString()+" <-- data");
                            ubications.add(u);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Log.d("exampleRepFire2", ubications+" <-- data");

//                ArrayList<Report> reportes = new ArrayList<Report>();
//            //    ArrayList<Person> persons = new ArrayList<Person>();
//                Log.d("exampleRepFire2", "showing reports");
//                MusicfitFireBase musicfitFireBase = new MusicfitFireBase();
//
//                musicfitFireBase.getChild("reports").addValueEventListener(new ValueEventListener() {
//             //   musicfitFireBase.getChild("Persons").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //        persons.clear();
//                        reportes.clear();
//                        for(DataSnapshot objSnaptshot : snapshot.getChildren()){
//                            Report r = objSnaptshot.getValue(Report.class);
//                //            Person p = objSnaptshot.getValue(Person.class);
//                            Log.d("exampleRepFire2", r.toString()+"<<--- reportes lista");
//                            reportes.add(r);
//              //              persons.add(p);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }
        });

        rep_del_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("exampleRepFire2", "deleting report");
            }
        });



//        //controllAsync=false;
//        /*modelDBRouteStretch= new DBModelStretchRoute();
//        StretchRoute stretchRoute= new StretchRoute();
//        stretchRoute.setStart(new LatLng(5,5));
//        stretchRoute.setEnd(new LatLng(5,5));
//        stretchRoute.setId(1);
//        int responseInsert=modelDBRouteStretch.insertStretchRoute(getActivity(),stretchRoute);*/
//
//        this.trainingControllerPresenter = new TrainingControllerPresenter(this);
//        play_button = (ImageView)view.findViewById(R.id.play_button);
//        pause_button = (ImageView)view.findViewById(R.id.pause_button);
//        stop_button = (ImageView)view.findViewById(R.id.stop_button);
//
//        chronometer = (Chronometer)view.findViewById(R.id.chronometer);
//        lbl_play = (TextView)view.findViewById(R.id.lbl_play);
//        lbl_pause = (TextView)view.findViewById(R.id.lbl_pause);
//        lbl_stop = (TextView)view.findViewById(R.id.lbl_stop);
//        lbl_map = (TextView)view.findViewById(R.id.lbl_map);
//        image_runner = (ImageView)view.findViewById(R.id.imageRunner);
//
//        //results part
//        lbl_no_resultados = (TextView)view.findViewById(R.id.lbl_no_resultados);
//        lbl_resultados = (TextView)view.findViewById(R.id.lbl_resultados);
//        lbl_km = (TextView)view.findViewById(R.id.lbl_km);
//        lbl_pasos = (TextView)view.findViewById(R.id.lbl_pasos);
//        lbl_cals = (TextView)view.findViewById(R.id.lbl_cals);
//        number_km = (TextView)view.findViewById(R.id.number_km);
//        number_pasos = (TextView)view.findViewById(R.id.number_pasos);
//        number_cals = (TextView)view.findViewById(R.id.number_cals);
//
//
//        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                long time= SystemClock.elapsedRealtime() - chronometer.getBase();
//                int h = (int)(time/3600000);
//                int m = (int)(time - h*3600000)/60000;
//                int s = (int)(time - h*3600000- m*60000)/1000;
//                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
//                chronometer.setText(t);
//            }
//        });
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.setText("00:00:00");
//
//        play_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startTraining();
//            }
//        });
//        pause_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseTraining();
//            }
//        });
//        stop_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopTraining();
//            }
//        });
//
//
//
        return this.view;
    }


    @Override
    public void startTraining() {


//        running = true;
//        image_runner.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.training_image_state_2));
//        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet - time_gone);
//        chronometer.start();
//        //controllAsync=true;
//       // executeAsync();
//
//
//        //just for animation
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                play_button.setVisibility(View.INVISIBLE);
//                pause_button.setVisibility(View.VISIBLE);
//                stop_button.setVisibility(View.VISIBLE);
//
//
//                lbl_play.setVisibility(View.INVISIBLE);
//                lbl_pause.setVisibility(View.VISIBLE);
//                lbl_stop.setVisibility(View.VISIBLE);
//                lbl_map.setVisibility(View.VISIBLE);
//
//                lbl_no_resultados.setVisibility(View.INVISIBLE);
//                lbl_resultados.setVisibility(View.VISIBLE);
//                lbl_pasos.setVisibility(View.VISIBLE);
//                lbl_km.setVisibility(View.VISIBLE);
//                lbl_cals.setVisibility(View.VISIBLE);
//                number_pasos.setVisibility(View.VISIBLE);
//                number_km.setVisibility(View.VISIBLE);
//                number_cals.setVisibility(View.VISIBLE);
//            }
//        },delay_buttons);
    }

    @Override
    public void pauseTraining() {
//        running = false;
//        image_runner.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.training_image_state_1));
//        chronometer.stop();
//        pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
//        //controllAsync=false;
//
//        //Toast.makeText(getActivity(),"yeah pause works",Toast.LENGTH_SHORT).show();
//        //just for animation
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                play_button.setVisibility(View.VISIBLE);
//                pause_button.setVisibility(View.INVISIBLE);
//
//                lbl_play.setText("Continuar");
//
//                lbl_play.setVisibility(View.VISIBLE);
//                lbl_pause.setVisibility(View.INVISIBLE);
//
//            }
//        },delay_buttons);
    }

    @Override
    public void stopTraining() {
//        running = false;
//        time_gone = 0;
//        image_runner.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.training_image_state_1));
//        chronometer.stop();
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        pauseOffSet = 0;
//        chronometer.setText("00:00:00");
//
//        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
//        editor = prefs.edit();
//        editor.putLong("pauseOffSet", 0);
//        editor.apply();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                play_button.setVisibility(View.VISIBLE);
//                pause_button.setVisibility(View.INVISIBLE);
//                stop_button.setVisibility(View.INVISIBLE);
//                map_button.setVisibility(View.INVISIBLE);
//
//                lbl_play.setText("Iniciar Entrenamiento");
//
//                lbl_play.setVisibility(View.VISIBLE);
//                lbl_pause.setVisibility(View.INVISIBLE);
//                lbl_stop.setVisibility(View.INVISIBLE);
//                lbl_map.setVisibility(View.INVISIBLE);
//
//                lbl_no_resultados.setVisibility(View.VISIBLE);
//                lbl_resultados.setVisibility(View.INVISIBLE);
//                lbl_pasos.setVisibility(View.INVISIBLE);
//                lbl_km.setVisibility(View.INVISIBLE);
//                lbl_cals.setVisibility(View.INVISIBLE);
//                number_pasos.setVisibility(View.INVISIBLE);
//                number_km.setVisibility(View.INVISIBLE);
//                number_cals.setVisibility(View.INVISIBLE);
//
//            }
//        },delay_buttons);
    }

    @Override
    public void mapTraining() {
//        Toast.makeText(getActivity(),"I'm the map",Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                //TrainingControllerFragment.this.trainingControllerPresenter.stopTraining();
//            }
//        },delay_buttons);
    }

    @Override
    public void viewTrainingReport(Report training) {
        FragmentManager.fragmentManager.changeFragment(new TrainingReportFragment(training));
    }

    @Override
    public void onStop() {//-------- cuando se cambia de fragment
        super.onStop();

//        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
//        editor = prefs.edit();
//        editor.putLong("time_gone", SystemClock.elapsedRealtime());
//        editor.putLong("getBase",chronometer.getBase());
//        editor.putLong("pauseOffSet",pauseOffSet);
//        editor.putBoolean("running",running);
//        editor.apply();

    }

    @Override
    public void onStart() {//cuando el fragment carga, se cargan todas los shared preferences sobre el tiempo
        super.onStart();
//        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
//        chronometer.setBase(prefs.getLong("getBase",0));
//        time_gone = SystemClock.elapsedRealtime() - chronometer.getBase();
//        running = prefs.getBoolean("running", false);
//        pauseOffSet = prefs.getLong("pauseOffSet",0);
//
//        if(running){
//            pauseOffSet = 0;
//            startTraining();
//        }else{
//            time_gone = -100;
//            startTraining();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    pauseTraining();
//                }
//            },100);
//        }
//        time_gone = 0;
//        prefs = this.getActivity().getSharedPreferences("prefsChrono", Context.MODE_PRIVATE);
//        editor = prefs.edit();
//        editor.putLong("time_gone", 0);
//        editor.apply();
    }

}
class Person{
    public String name;
    public int age;

    Person(){}
    Person (String name, int age){
        this.name = name;
        this.age = age;
    }
    public String toString (){
        return "name: "+this.name+ " - age: "+this.age;
    }
}