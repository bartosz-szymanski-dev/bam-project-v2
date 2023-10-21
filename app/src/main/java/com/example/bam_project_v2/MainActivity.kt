package com.example.bam_project_v2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bam_project_v2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val userName = binding.editText.text.toString()
            val intent = Intent(this, UserActivity::class.java).apply {
                putExtra("username", userName)
            }
            startActivity(intent)
        }
    }

    fun startUserActivity(view: View) {
        val userName = findViewById<EditText>(R.id.editText).text.toString()
        val userActivityIntent = Intent(this, UserActivity::class.java).apply {
            putExtra("username", userName)
        }
        startActivity(userActivityIntent)
    }
}