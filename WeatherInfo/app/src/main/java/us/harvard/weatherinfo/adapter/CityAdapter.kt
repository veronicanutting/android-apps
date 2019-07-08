package us.harvard.weatherinfo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_row.view.*
import us.harvard.weatherinfo.R
import us.harvard.weatherinfo.ScrollingActivity
import us.harvard.weatherinfo.WeatherActivity

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>{

    var cities = mutableListOf<String>()

    val context: Context
    constructor(context: Context, cityList: List<String>) : super() {
        this.context = context

        cities.addAll(cityList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        val todoView = LayoutInflater.from(context).inflate(
            R.layout.city_row, parent, false
        )
        return ViewHolder(todoView)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        var city = cities.get(holder.adapterPosition)

        holder.tvCityName.text = city

        holder.btnSeeWeatherDetails.setOnClickListener{
            var intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra("city name", city)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCityName = itemView.tvCityName
        val btnSeeWeatherDetails = itemView.btnSeeWeatherDetails
    }

}