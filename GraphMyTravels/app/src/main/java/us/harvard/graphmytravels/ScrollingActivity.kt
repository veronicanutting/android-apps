package us.harvard.graphmytravels

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_scrolling.*
import us.harvard.graphmytravels.adapter.CountryAdapter

class ScrollingActivity : AppCompatActivity(), CountryDialog.CountryHandler {

    lateinit var countryAdapter : CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        fabGenerateGraph.setOnClickListener { view ->
            Snackbar.make(view, "Generate graph", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        fabAddCountry.setOnClickListener {
            CountryDialog().show(supportFragmentManager, "Dialog")
        }

        initRecyclerView()
    }

    var countries = mutableListOf<String>()

    private fun initRecyclerView() {
        countryAdapter = CountryAdapter(this, countries)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        recyclerCountries.layoutManager = layoutManager
        recyclerCountries.adapter = countryAdapter
    }

    override fun countryCreated(country: String) {
        countryAdapter.addCountry(country)
    }

}
