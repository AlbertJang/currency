package kr.co.curreny.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.curreny.api.CurrencyRepository
import kr.co.curreny.constant.Country
import kr.co.curreny.constant.Network
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel(private val currencyRepository: CurrencyRepository): ViewModel() {
    val timestamp = MutableLiveData<String>()
    val currency = MutableLiveData<Double>()

    val networkState = MutableLiveData<Network>()

    // date format
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")

    fun getCurrency(country: Country) {
        viewModelScope.launch {
            try {

                val response = currencyRepository.getCurrency(country.unit)

                if(response.isSuccessful) {
                    val currencyResponse = response.body()

                    // 시간
                    if(currencyResponse?.timestamp != null) {
                        val date = Date(currencyResponse.timestamp * 1000)
                        timestamp.postValue(dateFormat.format(date))
                    }

                    // 환율
                    val quotes = currencyResponse?.quotes
                    currency.postValue(quotes?.get("USD${country.unit}"))

                    networkState.postValue(Network.SUCCESS)
                } else {
                    networkState.postValue(Network.FAILED)
                }
            } catch (e: Exception) {
                println("fetch stats failed ${e.message}")
                networkState.postValue(Network.FAILED)
            }
        }
    }
}