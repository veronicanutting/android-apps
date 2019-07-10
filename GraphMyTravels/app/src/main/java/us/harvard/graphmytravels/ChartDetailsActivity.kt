package us.harvard.graphmytravels

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_chart_details.*

class ChartDetailsActivity : AppCompatActivity(), OnChartValueSelectedListener {

    lateinit var chartSelected : String
    val countriesVisited = listOf<String>("Kenya", "Tanzania", "Egypt", "Ethiopia",
        "Argentina", "Uruguay", "Chile", "Colombia", "Brazil", "Peru", "United States", "Mexico",
        "Guatemala", "Belize", "Costa Rica", "Cuba", "China", "Thailand", "Vietnam", "Cambodia",
        "India", "Nepal", "New Zealand", "Israel", "Jordan", "Palestine", "Spain", "France", "Italy",
        "Vatican", "United Kingdom", "Belgium", "Netherlands", "Iceland", "Sweden", "Russia", "Hungary",
        "Slovakia", "Czechia", "Austria", "Croatia", "Montenegro", "Greece")
    var countriesOfInterest = listOf<String>()
    var countriesOfInterestVisited = listOf<String>()
    var countriesOfInterestNotVisited = listOf<String>()
    var visited = PieEntry(0f,"")
    var notVisited = PieEntry(0f,"")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_details)

        chartSelected = intent.getStringExtra("chart name")
        tvChartName.text = chartSelected

        getCountriesOfInterest(chartSelected)
        findOverlap()
        setUpChartEntries()

        pieChart.setEntryLabelTextSize(12f)
        setUpPieChart()
        pieChart.setOnChartValueSelectedListener(this)
    }

    fun getCountriesOfInterest(chartSelected : String) {

        when (chartSelected){
            "EU" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.EU).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.EU)
            }
            "Mercosur" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Mercosur).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Mercosur)
            }
            "Roman Empire" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Roman_Empire).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Roman_Empire)
            }
            "USSR" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.USSR).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.USSR)
            }
            "Nazi Occupied" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Nazi_Occupied).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Nazi_Occupied)
            }
            "Austro-Hungarian Empire" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Austro_Hungarian_Empire).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Austro_Hungarian_Empire)
            }
            "Ottoman Ruled" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Ottoman_Ruled).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Ottoman_Ruled)
            }
            "British Empire" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.British_Empire).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.British_Empire)
            }
            "Have Made Moon Visits (via probes)" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Have_Made_Moon_Visits_via_probes).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Have_Made_Moon_Visits_via_probes)
            }
            "Countries with More Than 100 Million People" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Countries_with_More_Than_100_Million_People).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Countries_with_More_Than_100_Million_People)
            }
            "Countries with Less Than 1 Million People" -> {
                countriesOfInterest = this.resources.getStringArray(R.array.Countries_with_Less_Than_1_Million_People).toMutableList()
                tvChartDescription.text = this.resources.getString(R.string.Countries_with_Less_Than_1_Million_People)
            }
        }
    }

    fun findOverlap() {
        countriesOfInterestVisited = countriesOfInterest.intersect(countriesVisited).toList()
        countriesOfInterestNotVisited = countriesOfInterest.minus(countriesVisited)
    }

    fun setUpChartEntries() {
        notVisited = PieEntry(countriesOfInterestNotVisited.size.toFloat(), "countries of interest not visited")
        visited = PieEntry(countriesOfInterestVisited.size.toFloat(), "countries of interest visited")
    }

    fun setUpPieChart() {

        val entries = ArrayList<PieEntry>()
        entries.add(visited)
        entries.add(notVisited)

        val dataSet = PieDataSet(entries, "Countries in ${chartSelected}")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        val colors = ArrayList<Int>()
        colors.add(Color.GREEN)
        colors.add(Color.RED)
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLUE)

        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.invalidate()

    }

    override fun onNothingSelected() {
        tvOnValueSelectedExplanation.text = "Click on the pie pieces for more info!"
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == visited) {
            tvOnValueSelectedExplanation.text = "The countries in ${chartSelected} that you've visited are: $countriesOfInterestVisited"
        } else if (e == notVisited) {
            tvOnValueSelectedExplanation.text = "You've not yet visited $countriesOfInterestNotVisited in ${chartSelected}"
        }
    }

}