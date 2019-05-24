package com.example.dell.mylib;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Notification.Builder builder=new Notification.Builder(context);
        String a=intent.getStringExtra("date");
        String title=intent.getStringExtra("title");
        Notification notification=builder.setContentTitle("RETURN DATE").setContentText(title+"\n"+a).setSmallIcon(R.drawable.aj).build();
        Log.e("", "notification" );
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
}
