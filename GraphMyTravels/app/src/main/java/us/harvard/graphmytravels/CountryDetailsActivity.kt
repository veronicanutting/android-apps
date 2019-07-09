package us.harvard.graphmytravels

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_country_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.harvard.graphmytravels.data.Base64676307
import us.harvard.graphmytravels.network.CountryAPI

class CountryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        tvCountryName.text = intent.getStringExtra("country name")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val countryAPI = retrofit.create(CountryAPI::class.java)

        val call = countryAPI.getCountryDetails(tvCountryName.text.toString(), "true")

        call.enqueue(
            object: Callback<Base64676307> {
                override fun onFailure(call: Call<Base64676307>, t: Throwable) {

                }
                override fun onResponse(call: Call<Base64676307>, response: Response<Base64676307>) {
                    val countryBase : Base64676307? = response.body()
                    val flagIcon = response.body()?.flag

                    insertCountryDetails(countryBase)
                    insertFlagIcons(flagIcon)

                }
            }
        )
    }

    fun insertCountryDetails(countryBase : Base64676307?) {
        tvLatitude.text = "(${countryBase?.latlng}°,"
        tvLongitude.text = " ${countryBase?.latlng}°)"
        tvCapital.text = "${countryBase?.capital}"
        tvPopulation.text = "Population: ${countryBase?.population}°C"
        tvSize.text = "Area: ${countryBase?.area}%"
    }

    fun insertFlagIcons(flagIcon : String?) {
        Glide.with(this@CountryDetailsActivity).load(
            (flagIcon)).into(ivFlagIconLeft)

        Glide.with(this@CountryDetailsActivity).load(
            (flagIcon)).into(ivFlagIconRight)
    }

}