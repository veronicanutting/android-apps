package us.harvard.weatherinfo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_scrolling.*
import us.harvard.weatherinfo.adapter.CityAdapter

class ScrollingActivity : AppCompatActivity() {

    lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Add city", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        initRecyclerView()
    }

    fun initRecyclerView() {
        var cities = listOf<String>("London", "New York", "Budapest")
        cityAdapter = CityAdapter(this, cities)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        recyclerCities.layoutManager = layoutManager
        recyclerCities.adapter = cityAdapter
    }

}
