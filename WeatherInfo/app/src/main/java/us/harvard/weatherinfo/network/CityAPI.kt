package us.harvard.weatherinfo.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import us.harvard.weatherinfo.data.Base

interface CityAPI {

    @GET("data/2.5/weather")
    fun getWeather(@Query("q") city: String,
                   @Query("units") units: String,
                   @Query("appid") appid: String): Call<Base>}