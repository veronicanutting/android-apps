package us.harvard.graphmytravels.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.country_row.view.*
import us.harvard.graphmytravels.CountryDetailsActivity
import us.harvard.graphmytravels.R

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHolder>{

    var countriesVisited = mutableListOf<String>("Aruba", "Argentina", "Uruguay", "Chile", "Peru")

    val context: Context
    constructor(context: Context, countryList: List<String>) : super() {
        this.context = context
        countriesVisited.addAll(countryList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapter.ViewHolder {
        val countryView = LayoutInflater.from(context).inflate(
            R.layout.country_row, parent, false
        )
        return ViewHolder(countryView)
    }

    override fun getItemCount(): Int {
        return countriesVisited.size
    }

    override fun onBindViewHolder(holder: CountryAdapter.ViewHolder, position: Int) {
        var country = countriesVisited.get(holder.adapterPosition)

        holder.tvCountryName.text = country

        holder.btnSeeCountryDetails.setOnClickListener{
            var intent = Intent(context, CountryDetailsActivity::class.java)
            intent.putExtra("country name", country)
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener{
            removeCountry(holder.adapterPosition)
        }
    }

    fun addCountry(country: String){
        countriesVisited.add(country)
        notifyDataSetChanged()
    }

    private fun removeCountry(index: Int) {
        countriesVisited.removeAt(index)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCountryName = itemView.tvCountryName
        val btnSeeCountryDetails = itemView.btnSeeCountryDetails
        val btnDelete = itemView.btnDelete
    }

}