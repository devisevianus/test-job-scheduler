package com.devis.testjobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object {
        private const val JOB_ID = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }

        Util.scheduleJob(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(applicationContext, MyService::class.java))
        } else {
            stopService(Intent(applicationContext, MyService::class.java))
            startService(Intent(applicationContext, MyService::class.java))
        }

    }

    private fun startJob() {
        if (isJobRunning(this)) {
            Toast.makeText(this, "Job Service is already scheduled", Toast.LENGTH_SHORT).show()
            return
        }
        val mServiceComponent = ComponentName(this, TestJobService::class.java)
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
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(builder.build())
        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show()
    }

    private fun isJobRunning(context: Context): Boolean {
        var isScheduled = false
        val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        for (jobInfo in scheduler.allPendingJobs) {
            if (jobInfo.id == JOB_ID) {
                isScheduled = true
                break
            }
        }
        return isScheduled
    }

}
