package com.minghaoqin.q.cowr;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Sadiq on 12/8/2018.
 */

public class NotificationRec extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent dintent=new Intent(context,MainActivity.class);
        dintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,dintent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent).setSmallIcon(android.R.drawable.arrow_up_float).setAutoCancel(true).setContentTitle("Clothes Recommendation").setContentText("Click to see today's recommendation");

        notificationManager.notify(100,builder.build());



    }
}
