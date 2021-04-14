package com.example.waterdrinkremainder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class Notificare extends BroadcastReceiver {
    private  static final String channel_id = "channel_1";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        int id_notificare = intent.getIntExtra("id_notificare", 0);
        String mesaj = intent.getStringExtra("mesaj");

        //Pornire MainActivity la apasare pe notificare
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channel_id, "Notificare", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_drop)
                .setContentTitle("BEA APĂ !")
                .setContentText(mesaj)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(id_notificare, builder.build());
    }
}
