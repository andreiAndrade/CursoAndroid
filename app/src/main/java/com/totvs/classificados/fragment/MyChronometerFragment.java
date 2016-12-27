package com.totvs.classificados.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.totvs.classificados.App;
import com.totvs.classificados.R;

/**
 * Created by Totvs on 21/12/2016.
 */

public class MyChronometerFragment extends Fragment {
    private Chronometer mChronometer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chronometer, container);

        mChronometer = (Chronometer) view.findViewById(R.id.chronometer);

        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        mChronometer.start();
//    }

    @Override
    public void onResume() {
        super.onResume();

        Long currentTime = App.getApp(getActivity()).getCurrentTime();
        mChronometer.setBase(currentTime + SystemClock.elapsedRealtime());
        mChronometer.start();

    }

    @Override
    public void onPause() {
        super.onPause();

        App.getApp(getActivity())
                .setCurrentTime(mChronometer.getBase() - SystemClock.elapsedRealtime());
    }
}
