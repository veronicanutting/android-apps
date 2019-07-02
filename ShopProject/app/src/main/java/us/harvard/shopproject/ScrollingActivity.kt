package us.harvard.shopproject

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        fabAddShopItem.setOnClickListener {
            ShopItemDialog().show(supportFragmentManager, "Dialog")
        }

        fabDeleteAll.setOnClickListener { view ->
            Snackbar.make(view, "Will delete all items", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}
