package com.nykopings.bonetider;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String CHANNEL_1_ID = "channel1";
        String TAG = "NotificationReceiver";
        String timeAlarm = intent.getStringExtra("timeAlarm");
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_logo)
                            .setContentTitle("Time for " + timeAlarm + " prayer")
                            .setContentText("Do not miss the prayer")
                            .setColor(context.getResources().getColor(R.color.black))
                            .setSound(alarmSound)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setVibrate(new long[]{0, 100, 200, 300})
                            .setAutoCancel(true);



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_1_ID,
                        "Channel 1",
                        NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(CHANNEL_1_ID);
            }

            mNotificationManager.notify(0, mBuilder.build());

        }
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle("Time for " + timeAlarm + " prayer")
                        .setContentText("Do not miss the prayer")
                        .setColor(context.getResources().getColor(R.color.black))
                        .setSound(alarmSound)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setVibrate(new long[]{0, 100, 200, 300})
                        .setAutoCancel(true);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(CHANNEL_1_ID);
        }

        mNotificationManager.notify(0, mBuilder.build());

//        String timeAlarm = intent.getStringExtra("timeAlarm");
//
//        long when = System.currentTimeMillis();
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
//                context, CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_logo)
//                .setContentTitle("Time for "+timeAlarm+" prayer")
//                .setContentText("Do not miss the prayer")
//                .setColor(context.getResources().getColor(R.color.black))
//                .setSound(alarmSound)
//                .setAutoCancel(true)
//                .setWhen(when)
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setVibrate(new long[]{0, 100, 200, 300})
//                .setAutoCancel(true);;
//        notificationManager.notify(0, mNotifyBuilder.build());


    }
}
