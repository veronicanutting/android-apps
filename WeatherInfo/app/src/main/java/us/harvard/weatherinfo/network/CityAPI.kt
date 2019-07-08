package us.harvard.weatherinfo.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import us.harvard.weatherinfo.data.Base

interface CityAPI {

    @GET("/weather")
    fun getWeather(@Query("base") base: String): Call<Base>
}