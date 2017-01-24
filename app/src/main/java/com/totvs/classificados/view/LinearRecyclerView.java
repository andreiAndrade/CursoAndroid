package com.totvs.classificados.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.totvs.classificados.R;

/**
 * Created by Totvs on 20/12/2016.
 */

public class LinearRecyclerView extends RecyclerView {

    public LinearRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayoutManager(new LinearLayoutManager(context));

//        setBackground(ContextCompat.getDrawable(context, android.R.drawable.dialog_holo_light_frame));
    }
}
