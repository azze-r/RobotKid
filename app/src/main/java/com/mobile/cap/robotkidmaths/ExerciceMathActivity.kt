package com.mobile.cap.robotkidmaths

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cap.robotkidmaths.databinding.ActivityExcerciceMathBinding


class ExerciceMathActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExcerciceMathBinding

    var maxInt: Int = 10

    lateinit var textToSpeech: TextToSpeech

    private val answerButtons = arrayOfNulls<TextView>(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExcerciceMathBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)

        val niveau = intent.getIntExtra("niveau",1)

        maxInt = when (niveau) {
            2 -> {
                20
            }
            3 -> {
                50
            }
            4 -> {
                100
            }
            else -> {
                10
            }
        }


    }


    override fun onResume() {
        super.onResume()

        answerButtons[0] = binding.txtA;
        answerButtons[1] = binding.txtB
        answerButtons[2] = binding.txtC
        answerButtons[3] = binding.txtD

        generateQuestion();
    }

    fun generateQuestion() {
        val correctButtonIndex = (0..3).random()

        val randomNumberA =  (0..maxInt).random()
        val randomNumberb =  (0..maxInt).random()

        val bonneReponseIntent = Intent(
            this,
            BonneReponseActivity::class.java
        )

        val mauvaiseReponseIntent = Intent(
            this,
            MauvaiseReponseActivity::class.java
        )

        val answer = (randomNumberA+randomNumberb).toString()
        bonneReponseIntent.putExtra("answer", answer)
        mauvaiseReponseIntent.putExtra("answer", answer)

        val instruction =  "$randomNumberA + $randomNumberb = ?"
        binding.txtInstructions.text =instruction
        val goodAnswer = randomNumberA + randomNumberb

        answerButtons[correctButtonIndex]?.text = goodAnswer.toString();

        for (i in 0..3) {
            if (i != correctButtonIndex) {
                val wrongAnswer = generateWrongAnswer(goodAnswer);
                answerButtons[i]?.text = wrongAnswer.toString()
                answerButtons[i]?.setOnClickListener {
                    makeRobotSound()
                    startActivity(mauvaiseReponseIntent)
                }
            }
            else{
                answerButtons[i]?.setOnClickListener {
                    makeRobotSound()
                    startActivity(bonneReponseIntent)
                }
            }
        }



    }

    private fun generateWrongAnswer(goodAnswer: Int): Int {
        var wrongAnswer: Int

        // Garantit qu'au moins une des réponses incorrectes est différente de la réponse correcte
        do {
            wrongAnswer = (0..maxInt).random()
        } while (wrongAnswer == goodAnswer)
        return wrongAnswer
    }

    fun makeRobotSound(){
        val mediaPlayer = MediaPlayer.create(this, R.raw.robo_walk)
        mediaPlayer.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        //textToSpeech.shutdown()
    }
}