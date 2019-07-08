package us.harvard.weatherinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
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

                    tvCityName.text = "Error!!"

                }
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    val weatherBase : Base? = response.body()
                    tvLatitude.text = "(${weatherBase?.coord?.lat}°,"
                    tvLongitude.text = " ${weatherBase?.coord?.lon}°)"
                    tvPressure.text = "Atmospheric pressure: ${weatherBase?.main?.pressure}hPa"
                    tvTemperature.text = "Temperature: ${weatherBase?.main?.temp}°C"
                    tvDescription.text = "${weatherBase?.weather?.get(0)?.description}"
                    tvWind.text = "Wind speed: ${weatherBase?.wind?.speed}m/s"
                    tvHumidity.text = "Humidity: ${weatherBase?.main?.humidity}%"

                    Glide.with(this@WeatherActivity).load(
                        ("https://openweathermap.org/img/w/" + response.body()?.weather?.get(0)?.icon + ".png"))
                        .into(ivWeatherIconLeft)

                    Glide.with(this@WeatherActivity).load(
                        ("https://openweathermap.org/img/w/" + response.body()?.weather?.get(0)?.icon + ".png"))
                        .into(ivWeatherIconRight)
                }
            }
        )


    }
}
