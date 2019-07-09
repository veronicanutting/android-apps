package us.harvard.graphmytravels.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import us.harvard.graphmytravels.data.Base64676307

interface CountryAPI {

    @GET("rest/v2/name/")
    fun getCountryDetails(@Query("") country: String,
                          @Query("fullText") fullText: String): Call<Base64676307>}