package com.mazurets.geoquiz

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mazurets.geoquiz.databinding.ActivityMainBinding
import com.mazurets.geoquiz.model.Question
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var countAnswer = 0
    private var correctAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.btnTrue.setOnClickListener {
            if (checkPressed())
                return@setOnClickListener

            checkAnswer(true)
        }

        binding.btnFalse.setOnClickListener {
            if (checkPressed())
                return@setOnClickListener

            checkAnswer(false)
        }

        binding.btnNext.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.btnPrev.setOnClickListener {
            currentIndex = kotlin.math.abs((currentIndex - 1) % questionBank.size)
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        binding.mainText.setText(questionBank[currentIndex].textResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val resultMessage = if (userAnswer == questionBank[currentIndex].answer){
            correctAnswer++
            R.string.it_s_correct
        }
        else
            R.string.it_s_incorrect

        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show()

        questionBank[currentIndex].isPressed = true
        countAnswer++
        checkCountAnswer()
    }

    private fun checkPressed(): Boolean {
        if (questionBank[currentIndex].isPressed) {
            Toast.makeText(
                this,
                getString(R.string.you_have_already_given_the_answer),
                Toast.LENGTH_SHORT
            ).show()
            return true
        }
        return false
    }
    private fun checkCountAnswer(){
        if (countAnswer == questionBank.size){
            Toast.makeText(this,
                "Correct answers $correctAnswer out of $countAnswer",
                Toast.LENGTH_LONG).show()
        }

    }
}