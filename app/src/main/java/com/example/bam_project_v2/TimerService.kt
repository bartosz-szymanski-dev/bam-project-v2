package com.example.bam_project_v2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class TimerService : Service() {
    private var userName = ""
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val counters = mutableListOf<AtomicInteger>()
    private val jobs = mutableListOf<Job>()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        userName = intent?.getStringExtra("username") ?: "Unknown"
        val counter = AtomicInteger(0)
        counters.add(counter)
        val thisCounterIndex = counters.size - 1
        val job = serviceScope.launch {
            while (isActive && counters.contains(counter)) {
                val count = counter.incrementAndGet()
                Log.d("TimerService", "Counter value for service $thisCounterIndex: $count")
                delay(1000)
            }
        }
        jobs.add(job)

        // Send a broadcast with the username when the service is started
        val broadcastIntent = Intent("com.example.bam_project_v2.ACTION_SEND").apply {
            putExtra("username", userName)
            putExtra("number", counter.get())  // Send the initial counter value
        }
        sendBroadcast(broadcastIntent)

        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        val broadcastIntent = Intent("com.example.bam_project_v2.ACTION_SEND").apply {
            counters.forEachIndexed { index, counter ->
                putExtra("username", userName)
                putExtra("number", counter.get())
            }
        }
        sendBroadcast(broadcastIntent)
        jobs.forEach { it.cancel() }
    }
}