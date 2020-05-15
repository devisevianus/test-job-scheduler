package com.devis.testjobscheduler;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ScreenReceiver","onReceive : "+ Intent.ACTION_USER_PRESENT);
        if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_USER_PRESENT)) {
            Intent intent2 = new Intent();
            intent2.setClass(context, MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

            try {
                /*Intent intent2 = new Intent();
                intent2.setClass(context, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);*/

                pendingIntent.send(context, 0, intent2);
                Util.INSTANCE.scheduleJob(context);

                //Toast.makeText(context, "TEST LOCKSCREEN", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
