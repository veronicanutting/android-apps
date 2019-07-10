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
            object: Callback<List<Base>> {
                override fun onFailure(call: Call<List<Base>>, t: Throwable) {
                    if (t is IOException) {
                        Log.d("FAILURE", "Callback failed due to network problem")
                    } else {
                        Log.d("FAILURE", "Callback failed due to conversion problem")
                    }
                }
                override fun onResponse(call: Call<List<Base>>, response: Response<List<Base>>) {
                    val countryBase : Base? = response.body()?.get(0)

                    insertCountryDetails(countryBase)
                    insertFlagIcons(countryBase)

                }
            }
        )
    }

    fun insertCountryDetails(countryBase : Base?) {
        tvLatitude.text = "(${countryBase?.latlng?.get(0)}°,"
        tvLongitude.text = " ${countryBase?.latlng?.get(1)}°)"
        tvCapital.text = "${countryBase?.capital}"
        tvPopulation.text = "Population: ${countryBase?.population}"
        tvSize.text = "Total Area: ${countryBase?.area}"
    }

    fun insertFlagIcons(countryBase : Base?) {
        val flagIcon = "https://www.countryflags.io/" +
                countryBase?.alpha2Code.toString() + "/flat/64.png"

        Glide.with(this@CountryDetailsActivity).load(
            flagIcon).into(ivFlagIconLeft)

        Glide.with(this@CountryDetailsActivity).load(
            flagIcon).into(ivFlagIconRight)
    }

}