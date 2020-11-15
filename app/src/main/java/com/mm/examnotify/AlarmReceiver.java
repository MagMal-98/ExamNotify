package com.mm.examnotify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "CHANNEL_SAMPLE";

    @Override
    public void onReceive(Context context, Intent intent) {

        //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        int notificationId = intent.getIntExtra("notificationId", 0);

        String message = intent.getStringExtra("message");
        String hour = intent.getStringExtra("hour");
        String date = intent.getStringExtra("date");
        String all = message + " " + date + " " + hour + " " + notificationId;

        Intent mainIntent = new Intent(context, AlarmActivity.class);
        mainIntent.putExtra("message", message);
        mainIntent.putExtra("date", date);
        mainIntent.putExtra("hour", hour);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("EPLAN NOTIFICATION")
                .setContentText(all)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(notificationId, builder.build());
    }
}