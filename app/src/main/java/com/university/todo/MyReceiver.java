package com.university.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.io.Serializable;

public class MyReceiver extends BroadcastReceiver {


    public static final String BUNDLE_TASK_MODEL = "bund_task_m";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("bc", "onReceive: ");

        Serializable seria = intent.getSerializableExtra(BUNDLE_TASK_MODEL);

        if (seria != null) {

            TaskModel model = (TaskModel) seria ;


            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notification = new NotificationCompat.Builder(context, "id123")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(model.getTitle())
                    .setContentText(model.getDescription())
                    .setAutoCancel(true)
                    .setVibrate(new long[]{100,230,1000,50,500,250,500})
                    .setSound(uri)
                    .build();

//            NotificationManager notiManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationManagerCompat.from(context)
                    .notify(13,notification);


            Log.d("bc", "onReceive: done");

        }
    }
}
