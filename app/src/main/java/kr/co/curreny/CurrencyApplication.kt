package kr.co.curreny

import android.app.Application
import kr.co.curreny.di.networkModule
import kr.co.curreny.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CurrencyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CurrencyApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}