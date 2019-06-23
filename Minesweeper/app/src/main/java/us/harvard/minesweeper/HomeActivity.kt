package us.harvard.minesweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnStart.setOnClickListener {

            val idCheck = rgrpLevels.checkedRadioButtonId
            var r1 = findViewById (idCheck) as RadioButton
            var r1text = r1.text

            var intentMain = Intent(this@HomeActivity,
                MainActivity::class.java)
            intentMain.putExtra("Level",r1text)

            startActivity(intentMain)
        }
    }

}
