package kr.co.curreny.di

import kr.co.curreny.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel {
        CurrencyViewModel(get())
    }
}