package com.example.bam_project_v2

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bam_project_v2.databinding.ActivityUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private val numberReceiver = NumberReceiver()
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("username").toString()
        binding.textView.text = userName

        val filter = IntentFilter("com.example.bam_project_v2.ACTION_SEND")
        registerReceiver(numberReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(numberReceiver)
    }

    fun startService(view: View) {
        val serviceIntent = Intent(this, TimerService::class.java).apply {
            putExtra("username", userName)
        }
        startService(serviceIntent)
    }

    fun stopService(view: View) {
        val serviceIntent = Intent(this, TimerService::class.java)
        stopService(serviceIntent)
    }

    fun fetchData(view: View) {
        val db = AppDatabase.getDatabase(this)

        CoroutineScope(Dispatchers.IO).launch {
            val data = db.userNumberDao().getAll()
            data.forEach {
                Log.d("UserActivity", "Fetched username: ${it.username}, number: ${it.number}")
            }
        }
    }
}