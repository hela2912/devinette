package tn.mpdam1.myapplication_devinette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var secretNumber: Int = 0
    private var attempts = 0
    private var isGameOver = false
    private lateinit var feedbackText: TextView
    private lateinit var inputNumber: EditText
    private lateinit var checkButton: Button
    private lateinit var timer: Chronometer
    private lateinit var historyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        feedbackText = findViewById(R.id.feedbackText)
        inputNumber = findViewById(R.id.inputNumber)
        checkButton = findViewById(R.id.checkButton)
        timer = findViewById(R.id.timer)
        historyText = findViewById(R.id.historyText)
        val instructionText = findViewById<TextView>(R.id.instructionText)

        val history = mutableListOf<String>()

        // Generate random minimum and maximum values for the range
        val minRange = (1..10).random() // Adjust the range as needed
        val maxRange = minRange + (1..10).random() // Adjust the range as needed

        // Generate a random number within the range
        secretNumber = (minRange..maxRange).random()

        // Display the initial instruction with the range
        instructionText.text = "Guess the number between $minRange and $maxRange"

        checkButton.setOnClickListener {
            val input = inputNumber.text.toString().toIntOrNull()
            if (!isGameOver && input != null) {
                attempts++
                val feedback = when {
                    input < secretNumber -> "$input is too low! Try a higher number."
                    input > secretNumber -> "$input is too high! Try a lower number."
                    else -> {
                        val winIntent = Intent(this, WinActivity::class.java)

                        // Calculate and pass the elapsed time to the WinActivity
                        val elapsedMillis = SystemClock.elapsedRealtime() - timer.base
                        winIntent.putExtra("elapsedMillis", elapsedMillis)

                        // Pass the number of attempts to the WinActivity
                        winIntent.putExtra("attempts", attempts)

                        startActivity(winIntent)
                        finish() // Close the game activity
                        isGameOver = true
                        "Congratulations! You guessed the number $input in $attempts attempts."
                    }
                }

                history.add(feedback)
                historyText.text = history.joinToString("\n")
            }
        }
        val startTime = SystemClock.elapsedRealtime()
        timer.base = startTime
        timer.start()
    }
}
