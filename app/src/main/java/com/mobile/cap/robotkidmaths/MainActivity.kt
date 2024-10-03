package com.mobile.cap.robotkidmaths

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cap.robotkidmaths.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)

        binding.constraintA.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
            mediaPlayer.start()
            val myIntent = Intent(
                this,
                ChoixNiveau::class.java
            )
            startActivity(myIntent)
        }

        binding.constraintB.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
            mediaPlayer.start()
            val myIntent = Intent(
                this,
                ChoixNiveau::class.java
            )
            startActivity(myIntent)
        }

    }
}