package com.totvs.classificados.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

/**
 * Created by Totvs on 23/01/2017.
 */

public class ToastService extends IntentService {

    public static final String MSG_KEY = "MSG_KEY";
    private final String TAG = "ToastService";

    public ToastService() {
        super("ToastService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String msg = intent.getStringExtra(MSG_KEY);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(5 * 1000); //5seg
                    } catch (InterruptedException e) {
                        Log.d(TAG, "Zuooooouu");
                        e.printStackTrace();
                    }

                    Log.d(TAG, msg);
                }
            }
        }).start();
    }
}
