package tn.mpdam1.myapplication_devinette

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val easyLevelButton = findViewById<Button>(R.id.easyLevelButton)
        val expertLevelButton = findViewById<Button>(R.id.expertLevelButton)

        easyLevelButton.setOnClickListener {
            val easyIntent = Intent(this, MainActivity::class.java)
            easyIntent.putExtra("level", "easy")
            startActivity(easyIntent)
        }

        expertLevelButton.setOnClickListener {
            val expertIntent = Intent(this, ExpertActivity::class.java)
            expertIntent.putExtra("level", "expert")
            startActivity(expertIntent)
        }
    }
}