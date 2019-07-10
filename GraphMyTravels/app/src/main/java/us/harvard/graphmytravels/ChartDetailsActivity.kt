package us.harvard.graphmytravels

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.Resource
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_chart_details.*

class ChartDetailsActivity : AppCompatActivity(), OnChartValueSelectedListener {

    lateinit var chartSelected : String
    val countriesVisited = listOf<String>("France", "Spain", "Mexico", "Argentina")
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
            else -> {
                countriesOfInterest = listOf<String>("France", "Spain", "Brazil")
                tvChartDescription.text = this.resources.getString(R.string.OtherChart)
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