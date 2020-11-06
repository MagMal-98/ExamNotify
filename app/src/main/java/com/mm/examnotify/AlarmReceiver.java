package com.mm.examnotify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Date;
import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "CHANNEL_SAMPLE";
    //public int notification_id = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        //int notificationId = intent.getIntExtra("notificationId", id(notification_id));
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        String message = intent.getStringExtra("message");

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("EPLAN NOTIFICATION")
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(m, builder.build());
    }

}