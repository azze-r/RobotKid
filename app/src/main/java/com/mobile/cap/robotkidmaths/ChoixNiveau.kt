package com.mobile.cap.robotkidmaths

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cap.robotkidmaths.databinding.ActivityBonneReponseBinding
import com.mobile.cap.robotkidmaths.databinding.ActivityChoixNiveauBinding

class ChoixNiveau : AppCompatActivity() {
    private lateinit var binding: ActivityChoixNiveauBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChoixNiveauBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)

        val exerciceIntent = Intent(
            this,
            ExerciceMathActivity::class.java)

        binding.imgA.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
            mediaPlayer.start()
            exerciceIntent.putExtra("niveau",1)
            startActivity(exerciceIntent)
        }

        binding.imgB.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
            mediaPlayer.start()
            exerciceIntent.putExtra("niveau",2)
            startActivity(exerciceIntent)
        }

        binding.imgC.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
            mediaPlayer.start()
            exerciceIntent.putExtra("niveau",3)
            startActivity(exerciceIntent)
        }

        binding.imgD.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
            mediaPlayer.start()
            exerciceIntent.putExtra("niveau",4)
            startActivity(exerciceIntent)
        }
    }

    override fun onBackPressed() {
        //
    }
}