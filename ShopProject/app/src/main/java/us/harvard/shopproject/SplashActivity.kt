package us.harvard.shopproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var anim = AnimationUtils.loadAnimation(this@SplashActivity,
            R.anim.splash_anim)

        anim.setAnimationListener(
            object:Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var intentScrolling = Intent(this@SplashActivity,
                        ScrollingActivity::class.java)

                    startActivity(intentScrolling)

                }

                override fun onAnimationStart(p0: Animation?) {
                }
            }
        )

        setContentView(R.layout.activity_splash)
        btnSplash.startAnimation(anim)

    }
}
