package us.harvard.graphmytravels

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_country_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.harvard.graphmytravels.data.Base
import us.harvard.graphmytravels.network.CountryAPI
import java.io.IOException

class CountryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        tvCountryName.text =  intent.getStringExtra("country name")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val countryDetailsAPI = retrofit.create(CountryAPI::class.java)

        val call = countryDetailsAPI.getCountryDetails(tvCountryName.text.toString(), "true")

        call.enqueue(
            object: Callback<Base> {
                override fun onFailure(call: Call<Base>, t: Throwable) {

                    if (t is IOException) {
                        Log.d("FAILURE", "CALLBACK FAILED DUE TO NETWORK")

                    } else {
                        Log.d("FAILURE", "CALLBACK FAILED DUE TO CONVERSION PROBABLY")
                    }
                }
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    Log.d("RESPONSE", "The response apparently is: ${response.body().toString()}")

                    val countryBase : Base? = response.body()
                    val flagIcon = countryBase?.flag

                    insertCountryDetails(countryBase)
                    insertFlagIcons(flagIcon)

                }
            }
        )
    }

    fun insertCountryDetails(countryBase : Base?) {
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