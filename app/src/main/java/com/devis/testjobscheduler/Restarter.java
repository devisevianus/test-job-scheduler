package com.devis.testjobscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast Listened", "Service tried to stop");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, MyService.class));
            } else {
                context.stopService(new Intent(context, MyService.class));
                context.startService(new Intent(context, MyService.class));
            }
            Util.INSTANCE.scheduleJob(context);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
