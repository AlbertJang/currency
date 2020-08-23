package kr.co.curreny.api

import retrofit2.awaitResponse

class CurrencyRepository(private val currencyApi: CurrencyApi) {
    suspend fun getCurrency(country: String) = currencyApi.getCurrency(country).awaitResponse()
}