package com.totvs.classificados.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Totvs on 23/01/2017.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            SmsMessage msg = Telephony.Sms.Intents.getMessagesFromIntent(intent)[0];

            String toastText =
                    String.format("From: %s\nMsg: %s", msg.getOriginatingAddress(), msg.getMessageBody());
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        }
    }
}
