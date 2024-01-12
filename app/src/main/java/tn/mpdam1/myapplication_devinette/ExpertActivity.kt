package tn.mpdam1.myapplication_devinette

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ExpertActivity : AppCompatActivity() {
    private var secretNumber: Int = 0
    private var attempts = 0
    private var isGameOver = false
    private lateinit var feedbackText: TextView
    private lateinit var inputNumber: EditText
    private lateinit var checkButton: Button
    private lateinit var timer: Chronometer
    private lateinit var historyText: TextView
    private var gameDurationMillis = 20000 // 20 seconds in milliseconds
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expert)


        feedbackText = findViewById(R.id.feedbackText)
        inputNumber = findViewById(R.id.inputNumber)
        checkButton = findViewById(R.id.checkButton)
        timer = findViewById(R.id.timer)
        historyText = findViewById(R.id.historyText)
        val lastGuessText = findViewById<TextView>(R.id.lastGuess)
        val instructionText = findViewById<TextView>(R.id.instructionText)

        val history = mutableListOf<String>()


        val minRange = (1..10).random()
        val maxRange = minRange + (1..10).random()


        secretNumber = (minRange..maxRange).random()


        instructionText.text = "Guess the number between $minRange and $maxRange"


        countDownTimer = object : CountDownTimer(gameDurationMillis.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the chronometer with the remaining time
                timer.base = SystemClock.elapsedRealtime() + millisUntilFinished
            }

            override fun onFinish() {

                finishGame()
            }
        }


        countDownTimer?.start()

        checkButton.setOnClickListener {
            val input = inputNumber.text.toString().toIntOrNull()
            if (!isGameOver && input != null) {
                attempts++
                val feedback = when {
                    input < secretNumber -> "$input is too low! Try a higher number."
                    input > secretNumber -> "$input is too high! Try a lower number."
                    else -> {
                        val winIntent = Intent(this, WinexpertActivity::class.java)
                        val elapsedMillis = gameDurationMillis
                        winIntent.putExtra("elapsedMillis", elapsedMillis)
                        winIntent.putExtra("attempts", attempts)
                        winIntent.putExtra("lastGuess", "Your last guess: $input (You win)")
                        startActivity(winIntent)
                        finish()
                        isGameOver = true
                        "Congratulations! You guessed the number $input in $attempts attempts."
                    }
                }

                history.add(feedback)
                historyText.text = history.joinToString("\n")
                lastGuessText.text = feedback

                Handler().postDelayed({
                    lastGuessText.text = ""
                }, 4000) // 4000 milliseconds (4 seconds)
            }
        }
    }

    private fun finishGame() {
        val loseIntent = Intent(this, WinexpertActivity::class.java)
        startActivity(loseIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the CountDownTimer to avoid memory leaks
        countDownTimer?.cancel()
    }
}