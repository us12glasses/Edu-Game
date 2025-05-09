package com.ghozi.game

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ghozi.game.databinding.ActivityGameBinding
import com.ghozi.game.databinding.ScoreDialogBinding
import kotlin.collections.shuffle

class GameActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: MutableList<QuestionModel> = mutableListOf()
        var time: String = ""
    }

    lateinit var binding: ActivityGameBinding

    var currentQuestionIndex = 0
    var selectedAnswer = ""
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@GameActivity)
            btn1.setOnClickListener(this@GameActivity)
            btn2.setOnClickListener(this@GameActivity)
            btn3.setOnClickListener(this@GameActivity)
            nextBtn.setOnClickListener(this@GameActivity)
        }

        // Shuffle the question list
        questionModelList.shuffle()

        loadQuestion()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                // Finish the quiz
            }
        }.start()
    }

    private fun loadQuestion() {
        selectedAnswer = ""
        if (currentQuestionIndex == questionModelList.size) {
            finishGame()
            return
        }

        val currentQuestion = questionModelList[currentQuestionIndex]

        // Shuffle the options
        val options = currentQuestion.options.shuffled()

        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextview.text = currentQuestion.question
            btn0.text = options[0]
            btn1.text = options[1]
            btn2.text = options[2]
            btn3.text = options[3]
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button
        if (clickedBtn.id == R.id.next_btn) {
            // Next button has been clicked
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(applicationContext, "Please select answer to continue", Toast.LENGTH_SHORT).show()
                return
            }
            val currentQuestion = questionModelList[currentQuestionIndex]
            val correctAnswer = currentQuestion.correct

            if (selectedAnswer == correctAnswer) {
                score++
                Log.i("Score of quiz", score.toString())
            }
            currentQuestionIndex++
            loadQuestion()
        } else {
            // Options button is clicked
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.blue))
        }
    }

    private fun finishGame() {
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if (percentage > 60) {
                scoreTitle.text = "Congratulations!"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Try learn some more!"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener {
                finish()
            }
        }
        AlertDialog.Builder(this).setView(dialogBinding.root).setCancelable(false).show()
    }
    private fun questionShuffled() {
        for (i in questionModelList.indices) {
            val temp = questionModelList[i]
            val randomIndex = (i until questionModelList.size).random()
            questionModelList[i] = questionModelList[randomIndex]
            questionModelList[randomIndex] = temp
        }
    }
}
