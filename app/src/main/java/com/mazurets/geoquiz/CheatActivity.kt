package com.mazurets.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mazurets.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater).also { setContentView(it.root) }
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener {
            val answerText = if (answerIsTrue)
                R.string.true_button
            else
                R.string.false_button

            binding.answerTextView.setText(answerText)
            setAnswerForResult(true)
        }
    }

    private fun setAnswerForResult(isAnswerShow: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShow)
        }
        setResult(Activity.RESULT_OK, data)
    }
    companion object {
        const val EXTRA_ANSWER_SHOWN = "answer_shown"
        private const val EXTRA_ANSWER_IS_TRUE = "answer_is_true"
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}