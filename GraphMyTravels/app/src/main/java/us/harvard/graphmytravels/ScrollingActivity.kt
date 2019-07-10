package us.harvard.graphmytravels

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_scrolling.*
import us.harvard.graphmytravels.adapter.CountryAdapter

class ScrollingActivity : AppCompatActivity(), CountryDialog.CountryHandler, ChartDialog.ChartHandler {

    lateinit var countryAdapter : CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        fabGenerateGraph.setOnClickListener {
            ChartDialog().show(supportFragmentManager, "Dialog")
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

    override fun chartCreated(chart: String) {
        Toast.makeText(this, "Chart ${chart} created", Toast.LENGTH_LONG).show()

    }

}
