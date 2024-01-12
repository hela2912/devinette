package tn.mpdam1.myapplication_devinette

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WinActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        val elapsedMillis = intent.getLongExtra("elapsedMillis", 0)
        val attempts = intent.getIntExtra("attempts", 0)
        val elapsedMinutes = (elapsedMillis / 60000).toInt()
        val elapsedSeconds = ((elapsedMillis % 60000) / 1000).toInt()
        val timeSpent = String.format("%02d:%02d", elapsedMinutes, elapsedSeconds)

        val intent = Intent(this, MainActivity::class.java)

        val playAgainButton = findViewById<Button>(R.id.playAgainButton)
        val menuButton = findViewById<Button>(R.id.menuButton)
        val winMessage = findViewById<TextView>(R.id.winMessage)
        val emojiImageView = findViewById<ImageView>(R.id.emojiImageView)

        // Display the win message including the time spent
        winMessage.text = "Congratulations! You guessed the number in $timeSpent and $attempts attempts."

        // Play the sound
        mediaPlayer = MediaPlayer.create(this, R.raw.win)
        mediaPlayer?.start()

        playAgainButton.setOnClickListener {
            startActivity(intent)
            finish()
        }

        menuButton.setOnClickListener {
            val mainMenuIntent = Intent(this, MenuActivity::class.java)
            startActivity(mainMenuIntent)
            finish()
        }

        // Set the emoji image based on win state
        // You can replace "R.drawable.your_win_emoji" with the actual resource for the emoji image.
        emojiImageView.setImageResource(R.drawable.imageshappy)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}
