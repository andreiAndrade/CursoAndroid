package com.totvs.classificados.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Totvs on 24/01/2017.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String text = "ALARM!!";
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();

    }
}
