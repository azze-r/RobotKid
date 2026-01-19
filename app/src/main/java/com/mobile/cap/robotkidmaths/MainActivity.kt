package com.mobile.cap.robotkidmaths

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cap.robotkidmaths.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null // On déclare le player ici

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // On définit l'action commune
        val onButtonClick = {
            playSoundAndNavigate()
        }

        binding.constraintA.setOnClickListener { onButtonClick() }
        binding.constraintB.setOnClickListener { onButtonClick() }
    }

    private fun playSoundAndNavigate() {
        // Libère le son précédent s'il existe pour éviter de saturer la mémoire
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
        mediaPlayer?.start()

        // Navigation
        val myIntent = Intent(this, ChoixNiveau::class.java)
        startActivity(myIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Très important : on libère le son quand on quitte l'activité
        mediaPlayer?.release()
        mediaPlayer = null
    }
}