package us.harvard.minesweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnStart.setOnClickListener {
            var intentMain = Intent(this@HomeActivity,
                MainActivity::class.java)

            intentMain.putExtra("Level",rgrpLevels.checkedRadioButtonId)

            val levelPicked = intent.getStringExtra("Level")

            Log.d("HOMELEVEL", "The level was $levelPicked")

            startActivity(intentMain)
        }
    }

}
