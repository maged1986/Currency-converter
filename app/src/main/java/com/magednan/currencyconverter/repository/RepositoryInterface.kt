package com.magednan.currencyconverter.repository

import androidx.lifecycle.LiveData
import com.magednan.currencyconverter.model.DailyResponse
import com.magednan.currencyconverter.model.HistoryItem
import com.magednan.currencyconverter.model.RateResponse
import com.magednan.currencyconverter.utils.Constants
import com.magednan.currencyconverter.utils.Resource
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryInterface {
    /*
    i made this interface to use in testing through test doubles
     */
    suspend fun convertCurrencies(
        apiKey: String = Constants.API_KEY,
        baseCurrency: String,
        symbols: String
    ): Resource<RateResponse>


    suspend fun getCurrenciesRates(
        apiKey: String = Constants.API_KEY,
        baseCurrency: String,
    ): Resource<RateResponse>


    suspend fun getDailyCurrenciesRates(
        apiKey: String,
        baseCurrency: String,
        symbols: String
    ): Resource<List<DailyResponse>>

    suspend fun upsert(historyItem: HistoryItem)

    fun getHistory(): LiveData<List<HistoryItem>>

    suspend fun deleteHistory(historyItem: HistoryItem)


}