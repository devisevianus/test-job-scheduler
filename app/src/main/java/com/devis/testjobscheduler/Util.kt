package com.devis.testjobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.widget.Toast

/**
 * Created by devis on 2020-02-03
 */
 
object Util {
    private const val JOB_ID = 10

    fun scheduleJob(context: Context) {
        val mServiceComponent = ComponentName(context, TestJobService::class.java)
        val builder = JobInfo.Builder(JOB_ID, mServiceComponent)
        builder.setRequiresDeviceIdle(false)
        builder.setRequiresCharging(false)
        builder.setMinimumLatency(1 * 1000)
        builder.setOverrideDeadline(3 * 1000)
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(900000) //15 menit
        } else {
            builder.setPeriodic(180000) //3 menit
        }*/
        val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(builder.build())
        Toast.makeText(context, "Job Service started", Toast.LENGTH_SHORT).show()
    }
}