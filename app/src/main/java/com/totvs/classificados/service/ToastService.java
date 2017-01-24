package com.totvs.classificados.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Totvs on 23/01/2017.
 */

public class ToastService extends IntentService {

    public static final String MSG_KEY = "MSG_KEY";
    private final String TAG = "ToastService";

    public static final String MSG_FITLER = "MSG_FITLER";

    public ToastService() {
        super("ToastService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        final String msg = intent.getStringExtra(MSG_KEY);

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(ToastService.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        makeMessage(msg, handler);
    }

    private void makeMessage(final String message, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;

                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(5 * 1000); //5seg
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message msg = new Message();
                    msg.obj = message;
                    handler.sendMessage(msg);

                    //Send Local Broadcast
//                    Intent intentBroadcast = new Intent(MSG_FITLER);
//                    intentBroadcast.putExtra(MSG_KEY, String.format(Locale.US, "%s: %d", message, i++));
//                    LocalBroadcastManager.getInstance(ToastService.this)
//                            .sendBroadcast(intentBroadcast);

//                    Toast.makeText(ToastService.this, "Jamal God", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }
}
