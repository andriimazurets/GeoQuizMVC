package com.mazurets.geoquiz

import androidx.lifecycle.ViewModel
import com.mazurets.geoquiz.model.Question

class QuizViewModel: ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentIndex = 0

    val questionBankSize = questionBank.size

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    var currentQuestionPressed: Boolean get() = questionBank[currentIndex].isPressed
        set(value) {this.questionBank[currentIndex].isPressed = true}

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = kotlin.math.abs((currentIndex - 1) % questionBank.size)
    }
}