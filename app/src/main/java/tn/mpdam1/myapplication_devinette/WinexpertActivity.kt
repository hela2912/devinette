package tn.mpdam1.myapplication_devinette

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WinexpertActivity : AppCompatActivity() {
    private lateinit var winMessage: TextView
    private lateinit var lastGuess: TextView
    private lateinit var winImage: ImageView
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expertwin)

        val intent = Intent(this, ExpertActivity::class.java)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)
        val menuButton = findViewById<Button>(R.id.menuButton)
        winMessage = findViewById(R.id.winMessage)
        lastGuess = findViewById(R.id.lastGuess)
        winImage = findViewById(R.id.winImage)
        mediaPlayer = MediaPlayer.create(this, R.raw.win) // Load the win sound

        val elapsedMillis = intent.getLongExtra("elapsedMillis", 0)
        val attempts = intent.getIntExtra("attempts", 0)
        val timeSpent = String.format("%02d:%02d", elapsedMillis / 60000, (elapsedMillis % 60000) / 1000)

        if (attempts <= 0) {
            winMessage.text = "You couldn't guess the number in time."
            playSound(R.raw.lose) // Play lose sound
            winImage.setImageResource(R.drawable.sad)
        } else {
            winMessage.text = "Congratulations! You guessed the number in $timeSpent and $attempts attempts."
            playSound(R.raw.win) // Play win sound
            winImage.setImageResource(R.drawable.imageshappy)
        }

        playAgainButton.setOnClickListener {
            startActivity(intent)
            finish()
        }

        menuButton.setOnClickListener {
            val mainMenuIntent = Intent(this, MenuActivity::class.java)
            startActivity(mainMenuIntent)
            finish()
        }

        Handler().postDelayed({
            lastGuess.text = ""
        }, 2000) // 2000 milliseconds (2 seconds)
    }

    override fun onResume() {
        super.onResume()
        val lastGuessText = intent.getStringExtra("lastGuess")
        lastGuess.text = lastGuessText
    }

    private fun playSound(soundResource: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, soundResource)
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}