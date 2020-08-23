package kr.co.curreny.di

import android.util.Log
import kr.co.curreny.api.CurrencyApi
import kr.co.curreny.api.CurrencyRepository
import kr.co.curreny.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single{
        val debugInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("API", message)
            }
        })
        debugInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(debugInterceptor)
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://api.currencylayer.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    // repository
    single{
        CurrencyRepository(get())
    }
}