package com.mazurets.geoquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.mazurets.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var countAnswer = 0
    private var correctAnswer = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

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
            quizViewModel.moveToNext()
            binding.btnPrev.visibility = View.VISIBLE
            updateQuestion()
        }

        binding.btnPrev.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        binding.btnCheat.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivity(intent)
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        binding.mainText.setText(quizViewModel.currentQuestionText)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val resultMessage = if (userAnswer == quizViewModel.currentQuestionAnswer) {
            correctAnswer++
            R.string.it_s_correct
        } else
            R.string.it_s_incorrect

        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show()

        quizViewModel.currentQuestionPressed = true
        countAnswer++
        checkCountAnswer()
    }

    private fun checkPressed(): Boolean {
        if (quizViewModel.currentQuestionPressed) {
            Toast.makeText(
                this,
                getString(R.string.you_have_already_given_the_answer),
                Toast.LENGTH_SHORT
            ).show()
            return true
        }
        return false
    }

    private fun checkCountAnswer() {
        if (countAnswer == quizViewModel.questionBankSize) {
            Toast.makeText(
                this,
                "Correct answers $correctAnswer out of $countAnswer",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val KEY_INDEX = "index"
    }
}