package com.example.mpandroidchart.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry

class LineChartViewModel : ViewModel() {

    private val _currencyEUR = MutableLiveData<ArrayList<Entry>>()
    val currencyEUR: LiveData<ArrayList<Entry>> = _currencyEUR

    private val _currencyUSD = MutableLiveData<ArrayList<Entry>>()
    val currencyUSD: LiveData<ArrayList<Entry>> = _currencyUSD

    private val _currencyRUB = MutableLiveData<ArrayList<Entry>>()
    val currencyRUB: LiveData<ArrayList<Entry>> = _currencyRUB

    fun generateEntries(number: Int) {
        _currencyEUR.value = generateEUREntry(number)
        Log.d("entry", "EUR ".plus(_currencyEUR.value))

        _currencyUSD.value = generateUSDEntry(number)
        Log.d("entry", "USD ".plus(_currencyUSD.value))

        _currencyRUB.value = generateRUBEntry(number)
        Log.d("entry", "RUB ".plus(_currencyRUB.value))

    }

    private fun generateUSDEntry(number: Int): ArrayList<Entry> {
        val currency = ArrayList<Entry>()
        currency.add(Entry(0f, 5.43f))
        currency.add(Entry(1f, 4.43f))
        currency.add(Entry(2f, 3.211f))
        currency.add(Entry(3f, 2.555f))
        currency.add(Entry(4f, 2.434f))
        if (number == 7) {
            currency.add(Entry(5f, 2.234f))
            currency.add(Entry(6f, 2f))
        }
        if (number == 12) {
            currency.add(Entry(5f, 2.11f))
            currency.add(Entry(6f, 2.111f))
            currency.add(Entry(7f, 4.543f))
            currency.add(Entry(8f, 4.111f))
            currency.add(Entry(9f, 3.251f))
            currency.add(Entry(10f, 2.251f))
            currency.add(Entry(11f, 2.961f))
        }
        return currency
    }

    private fun generateEUREntry(number: Int): ArrayList<Entry> {
        val currency = ArrayList<Entry>()
        currency.add(Entry(0f, 3.0231f))
        currency.add(Entry(1f, 4.2511f))
        currency.add(Entry(2f, 4.4344f))
        currency.add(Entry(3f, 5.211f))
        currency.add(Entry(4f, 5.11f))
        if (number == 7) {
            currency.add(Entry(5f, 2.534f))
            currency.add(Entry(6f, 2.694f))
        }
        if (number == 12) {
            currency.add(Entry(5f, 2.2644f))
            currency.add(Entry(6f, 2.444f))
            currency.add(Entry(7f, 1.944f))
            currency.add(Entry(8f, 2.544f))
            currency.add(Entry(9f, 3.244f))
            currency.add(Entry(10f, 2.944f))
            currency.add(Entry(11f, 3.944f))
        }
        return currency
    }

    private fun generateRUBEntry(number: Int): ArrayList<Entry> {
        val currency = ArrayList<Entry>()
        currency.add(Entry(0f, 4f))
        currency.add(Entry(1f, 3.644f))
        currency.add(Entry(2f, 4.244f))
        currency.add(Entry(3f, 4f))
        currency.add(Entry(4f, 2.644f))
        if (number == 7) {
            currency.add(Entry(5f, 2.844f))
            currency.add(Entry(6f, 2.43f))
        }
        if (number == 12) {
            currency.add(Entry(5f, 2.644f))
            currency.add(Entry(6f, 2.944f))
            currency.add(Entry(7f, 3f))
            currency.add(Entry(8f, 3.544f))
            currency.add(Entry(9f, 4f))
            currency.add(Entry(10f, 3.556f))
            currency.add(Entry(11f, 3.011f))
        }
        return currency
    }
}