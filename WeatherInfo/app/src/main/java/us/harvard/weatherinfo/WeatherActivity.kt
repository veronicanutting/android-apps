package us.harvard.weatherinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.city_row.*
import kotlinx.android.synthetic.main.city_row.tvCityName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.harvard.weatherinfo.data.Base
import us.harvard.weatherinfo.network.CityAPI

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        var name = intent.getStringExtra("city name")
        tvCityName.text = name


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherAPI = retrofit.create(CityAPI::class.java)

        val call = weatherAPI.getWeather(tvCityName.text.toString(),
                                    "metric",
                                    "db0fde52807ead72a721bae5d3446c86")

        call.enqueue(
            object: Callback<Base> {
                override fun onFailure(call: Call<Base>, t: Throwable) {

                }
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    val weatherBase : Base? = response.body()
                    tvLatitude.text = "Lat is ${weatherBase?.coord?.lat}"
                    tvLongitude.text = "Long is ${weatherBase?.coord?.lon}"

                    // http://openweathermap.org/img/wn/10d@2x.png --> "10d" is icon string
                }
            }
        )


    }
}
