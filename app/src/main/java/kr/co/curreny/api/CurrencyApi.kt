package kr.co.curreny.api

import kr.co.curreny.model.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("live")
    fun getCurrency(@Query("currencies") country: String): Call<CurrencyResponse>
}