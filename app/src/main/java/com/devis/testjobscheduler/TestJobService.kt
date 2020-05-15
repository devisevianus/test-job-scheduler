package com.devis.testjobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log

/**
 * Created by devis on 2020-02-01
 */

class TestJobService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("onStopJob", "Stopped")

        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("onStartJob", "Started")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(applicationContext, MyService::class.java))
        } else {
            stopService(Intent(applicationContext, MyService::class.java))
            startService(Intent(applicationContext, MyService::class.java))
        }

        Util.scheduleJob(applicationContext)

        return true
    }
}