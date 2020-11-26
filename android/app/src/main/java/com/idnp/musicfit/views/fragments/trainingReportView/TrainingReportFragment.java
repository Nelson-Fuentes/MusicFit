package com.idnp.musicfit.views.fragments.trainingReportView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.idnp.musicfit.R;
import com.idnp.musicfit.models.entities.Report;
import com.idnp.musicfit.models.entities.Training;
import com.idnp.musicfit.views.fragments.fragmentManager.FragmentManager;
import com.idnp.musicfit.presenter.trainingReportPresenter.TrainingReportPresenter;
import com.idnp.musicfit.presenter.trainingReportPresenter.iTrainingReportPresenter;


public class TrainingReportFragment extends Fragment implements iTrainingReportView{

    private View view;
    private iTrainingReportPresenter trainingReportPresenter;
    private ImageButton button;
    private ImageButton reportList;
    //objetos para animar las barras de animación
    //TextView reportViewDurationBar,reportViewKilometrosBar,reportViewCaloriasBar;


    public TrainingReportFragment(Report training) {
        this.trainingReportPresenter = new TrainingReportPresenter(this, training);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.view == null){
            this.view = inflater.inflate(R.layout.fragment_training_report, container, false);
        }

        button = (ImageButton) this.view.findViewById(R.id.buttonExit);
        reportList = (ImageButton) this.view.findViewById(R.id.buttonlist);

//        reportViewDurationBar=view.findViewById(R.id.report_view_duration_bar);
//        ObjectAnimator animation= ObjectAnimator.ofFloat(reportViewDurationBar,"translationX",500f,300f);
//        animation.setDuration(1000);
//        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(reportViewDurationBar, "alpha", 0.5f, 1.0f);
//        fadeAnim.setDuration(1000);
//
//        reportViewKilometrosBar=view.findViewById(R.id.report_view_km_bar);
//        ObjectAnimator animation2= ObjectAnimator.ofFloat(reportViewKilometrosBar,"translationX",500f,200f);
//        animation2.setDuration(1000);
//        ValueAnimator fadeAnim2 = ObjectAnimator.ofFloat(reportViewKilometrosBar, "alpha", 0.5f, 1.0f);
//        fadeAnim2.setDuration(1000);
//
//        reportViewCaloriasBar=view.findViewById(R.id.report_view_kcal_bar);
//        ObjectAnimator animation3= ObjectAnimator.ofFloat(reportViewCaloriasBar,"translationX",500f,250f);
//        animation3.setDuration(1000);
//        ValueAnimator fadeAnim3 = ObjectAnimator.ofFloat(reportViewCaloriasBar, "alpha", 0.5f, 1.0f);
//        fadeAnim3.setDuration(1000);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(animation).with(fadeAnim).with(animation2).with(fadeAnim2).with(animation3).with(fadeAnim3);
//        animatorSet.start();


        //reportViewAnimationBar.setAnimation(durationAnim);


        button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager.fragmentManager.remove(TrainingReportFragment.this);
                    }
                });
        reportList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager.fragmentManager.remove(TrainingReportFragment.this);
            }
        });
        return this.view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //iniciando el fragment de map

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, new TrainingMapFragment()).commit();
    }

}