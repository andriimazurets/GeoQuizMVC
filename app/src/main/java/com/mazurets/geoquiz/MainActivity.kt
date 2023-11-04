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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.btnTrue.setOnClickListener {
            checkAnswer(true)
        }

        binding.btnFalse.setOnClickListener {
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
        val resultMessage = if (userAnswer == questionBank[currentIndex].answer)
            R.string.it_s_correct
        else
            R.string.it_s_incorrect

        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show()
    }
}