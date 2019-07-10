package us.harvard.graphmytravels.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import us.harvard.graphmytravels.data.Base

interface CountryAPI {

    @GET("rest/v2/name/{country}")
    fun getCountryDetails(@Path("country") country: String,
                          @Query("fullText") fullText: String): Call<List<Base>>

}