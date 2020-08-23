package kr.co.curreny.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.curreny.R
import kr.co.curreny.constant.Country
import kr.co.curreny.constant.Network
import kr.co.curreny.viewmodel.CurrencyViewModel
import org.koin.android.ext.android.inject
import java.lang.NumberFormatException
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private val viewModel: CurrencyViewModel by inject()
    private var currentCountry: Country? = null
    private var currentCurrency: Double? = null

    // currency format
    var pattern = "###,###.##"
    var decimalFormat: DecimalFormat = DecimalFormat(pattern)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureObservables()
        configureListener()

        showCountryDialog()
    }

    private fun configureListener() {
        receiveLayout?.setOnClickListener {
            showCountryDialog()
        }

        amountEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                try {
                    val amount = s.toString().toInt()

                    if(amount in 1..10000) {
                        if(currentCurrency != null) {
                            val result = currentCurrency!! * amount.toDouble()

                            resultTextView?.text = "수취금액은 ${decimalFormat.format(result)} ${currentCountry?.unit} 입니다."

                            errorTextView?.visibility = View.GONE
                            resultTextView?.visibility = View.VISIBLE
                        } else {
                            amountEditText?.text?.clear()
                            showErrorDialog()
                        }
                    } else {
                        // error
                        errorTextView?.visibility = View.VISIBLE
                        resultTextView?.visibility = View.GONE
                    }
                }catch (e: NumberFormatException) {
                    // error
                    errorTextView?.visibility = View.VISIBLE
                    resultTextView?.visibility = View.GONE
                }
            }
        })
    }

    private fun configureObservables() {
        viewModel.timestamp.observe(this, Observer {
            timestampTextView?.text = it.toString()
        })

        viewModel.currency.observe(this, Observer { currency ->
            currentCurrency = currency
            currencyTextView?.text = "${decimalFormat.format(currency)} ${currentCountry?.unit} / USD"
        })

        viewModel.networkState.observe(this, Observer { networkState ->
            when(networkState){
                Network.SUCCESS -> {}
                Network.FAILED -> {

                }
            }
        })
    }

    private fun showCountryDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setItems(R.array.country) { _, which ->
            when(which) {
                Country.KOREA.position -> {
                    // krw
                    currentCountry = Country.KOREA
                    receiveTextView?.text = Country.KOREA.description
                    viewModel.getCurrency(Country.KOREA)
                }

                Country.JAPAN.position -> {
                    // jpy
                    currentCountry = Country.JAPAN
                    receiveTextView?.text = Country.JAPAN.description
                    viewModel.getCurrency(Country.JAPAN)
                }

                Country.PHILIPPINES.position -> {
                    //php
                    currentCountry = Country.PHILIPPINES
                    receiveTextView?.text = Country.PHILIPPINES.description
                    viewModel.getCurrency(Country.PHILIPPINES)
                }
            }
        }

        dialog.create().show()
    }

    fun showErrorDialog() {
        val dialog = AlertDialog.Builder(this)
            dialog.setMessage("수취국가를 선택해 주세요.")
            dialog.setNeutralButton("확인") { _, _ ->

            }

        dialog.create().show()
    }
}