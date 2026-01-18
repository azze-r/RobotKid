package com.mobile.cap.robotkidmaths

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cap.robotkidmaths.databinding.ActivityExcerciceMathBinding
import kotlin.random.Random

class ExerciceMathActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExcerciceMathBinding
    private val answerButtons = arrayOfNulls<TextView>(4)

    private var niveau = 1
    private var scoreConsecutif = 0
    private val PALIER = 3

    private var bonneReponse = 0
    private var questionTexte = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExcerciceMathBinding.inflate(layoutInflater)
        setContentView(binding.root)

        niveau = intent.getIntExtra("niveau", 1)

        answerButtons[0] = binding.txtA
        answerButtons[1] = binding.txtB
        answerButtons[2] = binding.txtC
        answerButtons[3] = binding.txtD

        // On met les clics
        setupClicks()

        updateUIProgression()
    }

    override fun onResume() {
        super.onResume()
        genererQuestion()


    }
    // -------------------------
    // UI PROGRESSION
    // -------------------------
    private fun updateUIProgression() {
        binding.txtNiveau.text = "Niveau $niveau"
        binding.progressBar.progress = scoreConsecutif
    }

    // -------------------------
    // QUESTION
    // -------------------------
    private fun genererQuestion() {

        // On utilise ta logique de niveau
        val operation = creerOperationSelonNiveau()

        questionTexte = operation.first
        bonneReponse = operation.second

        binding.txtInstructions.text = questionTexte

        genererReponses()
    }

    private fun genererReponses() {

        val reponses = mutableSetOf<Int>()
        reponses.add(bonneReponse)

        while (reponses.size < 4) {
            val faux = genererFausseReponse(bonneReponse)
            reponses.add(faux)
        }

        val liste = reponses.shuffled()

        binding.txtA.text = liste[0].toString()
        binding.txtB.text = liste[1].toString()
        binding.txtC.text = liste[2].toString()
        binding.txtD.text = liste[3].toString()
    }

    private fun setupClicks() {
        binding.constraintA.setOnClickListener { verifierReponse(binding.txtA.text.toString().toInt()) }
        binding.constraintB.setOnClickListener { verifierReponse(binding.txtB.text.toString().toInt()) }
        binding.constraintC.setOnClickListener { verifierReponse(binding.txtC.text.toString().toInt()) }
        binding.constraintD.setOnClickListener { verifierReponse(binding.txtD.text.toString().toInt()) }
    }

    private fun verifierReponse(reponse: Int) {
        if (reponse == bonneReponse) {
            bonneReponse()
        } else {
            mauvaiseReponse()
        }
    }

    // -------------------------
    // LOGIQUE DES NIVEAUX
    // -------------------------
    private fun creerOperationSelonNiveau(): Pair<String, Int> {
        return when (niveau) {
            1 -> addition(5)
            2 -> addition(10)
            3 -> addition(20)
            4 -> soustraction(10)
            5 -> soustraction(20)
            6 -> multiplication(5)
            7 -> multiplication(10)
            8 -> division(5)
            9 -> division(10)
            else -> operationMix()
        }
    }

    private fun operationMix(): Pair<String, Int> {
        return when (Random.nextInt(4)) {
            0 -> addition(20)
            1 -> soustraction(20)
            2 -> multiplication(10)
            else -> division(10)
        }
    }

    // -------------------------
    // OPERATIONS
    // -------------------------
    private fun addition(max: Int): Pair<String, Int> {
        val a = Random.nextInt(max + 1)
        val b = Random.nextInt(max + 1)
        return Pair("$a + $b = ?", a + b)
    }

    private fun soustraction(max: Int): Pair<String, Int> {
        val a = Random.nextInt(max + 1)
        val b = Random.nextInt(a + 1)
        return Pair("$a - $b = ?", a - b)
    }

    private fun multiplication(max: Int): Pair<String, Int> {
        val a = Random.nextInt(1, max + 1)
        val b = Random.nextInt(1, max + 1)
        return Pair("$a × $b = ?", a * b)
    }

    private fun division(max: Int): Pair<String, Int> {
        val b = Random.nextInt(1, max + 1)
        val resultat = Random.nextInt(1, max + 1)
        val a = b * resultat
        return Pair("$a ÷ $b = ?", resultat)
    }

    // -------------------------
    // REPONSES
    // -------------------------
    private fun genererFausseReponse(bonne: Int): Int {
        var faux: Int
        do {
            faux = bonne + Random.nextInt(-10, 11)
        } while (faux == bonne || faux < 0)
        return faux
    }

    private fun bonneReponse() {
        jouerSon()

        scoreConsecutif++

        if (scoreConsecutif >= PALIER && niveau < 10) {
            niveau++
            scoreConsecutif = 0
            animationMonteeNiveau()
        }

        updateUIProgression()

        val intent = Intent(this, BonneReponseActivity::class.java)
        intent.putExtra("answer", "${bonneReponse}")
        startActivity(intent)
    }

    private fun mauvaiseReponse() {
        jouerSon()
        scoreConsecutif = 0
        updateUIProgression()

        val intent = Intent(this, MauvaiseReponseActivity::class.java)
        intent.putExtra("answer", "${bonneReponse}")
        startActivity(intent)
    }

    // -------------------------
    // FEEDBACK
    // -------------------------
    private fun animationMonteeNiveau() {
        binding.txtNiveau.animate()
            .scaleX(1.4f)
            .scaleY(1.4f)
            .setDuration(250)
            .withEndAction {
                binding.txtNiveau.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(250)
            }
    }

    private fun jouerSon() {
        val mp = MediaPlayer.create(this, R.raw.robo_walk)
        mp.start()
        mp.setOnCompletionListener { it.release() }
    }
}
