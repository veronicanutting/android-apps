package us.harvard.minesweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRestart.setOnClickListener {
            vMinesweeper.resetGame()
        }

        btnHome.setOnClickListener {
            var intentHome = Intent(this@MainActivity,
                HomeActivity::class.java)

            startActivity(intentHome)
            finish() // return to home screen
        }

    }

    public fun isFlagChecked() = cbFlag.isChecked
}
