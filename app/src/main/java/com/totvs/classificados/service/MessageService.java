package com.totvs.classificados.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.totvs.classificados.R;
import com.totvs.classificados.activity.FilterActivity;

import java.util.Map;

/**
 * Created by Totvs on 27/01/2017.
 */

public class MessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String msg = remoteMessage.getNotification().getBody();

        Map<String, String> payload = remoteMessage.getData();

        if (msg != null) {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(payload.get("title"))
                    .setContentText(String.format("%s\n%s,%s", msg, payload.get("lat"), payload.get("lng")))
                    .setAutoCancel(true);

            Intent intent = new Intent(this, FilterActivity.class);
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            notification.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0, notification.build());
        }

    }

}
