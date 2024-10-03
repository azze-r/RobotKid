package com.mobile.cap.robotkidmaths

import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.cap.robotkidmaths.databinding.ActivityBonneReponseBinding
import com.mobile.cap.robotkidmaths.databinding.ActivityExcerciceMathBinding
import java.util.Locale

class BonneReponseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBonneReponseBinding
    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBonneReponseBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)
        val message = intent.getStringExtra("answer")
        binding.txtInstructions.text = message

        /*
        textToSpeech = TextToSpeech(this) {status ->
            if (status == TextToSpeech.SUCCESS){
                Log.d("TextToSpeech", "Initialization Success")
                textToSpeech.language = Locale.FRANCE
                textToSpeech.speak("Bonne réponse, le résultat est $message", TextToSpeech.QUEUE_FLUSH , null, "unique_id")
                Thread.sleep(3000)
                binding.bubble.visibility = View.VISIBLE
                binding.txtInstructions.visibility = View.VISIBLE
            }else{
                Log.d("TextToSpeech", "Initialization Failed")
            }
        }*/

        binding.bubble.visibility = View.VISIBLE
        binding.txtInstructions.visibility = View.VISIBLE

        binding.cardHome.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
            mediaPlayer.start()
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //textToSpeech.shutdown()
    }
}