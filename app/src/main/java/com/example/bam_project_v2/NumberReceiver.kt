package com.example.bam_project_v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumberReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val userName = intent?.getStringExtra("username")
        val number = intent?.getIntExtra("number", -1)
        Log.d("NumberReceiver", "Received username: $userName, number: $number")

        val userNumber = UserNumber(username = userName ?: "Unknown", number = number ?: -1)
        val db = AppDatabase.getDatabase(context!!)

        CoroutineScope(Dispatchers.IO).launch {
            db.userNumberDao().insert(userNumber)
        }
    }
}