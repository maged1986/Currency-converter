package com.magednan.currencyconverter.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.magednan.currencyconverter.historyDB.HistoryDao
import com.magednan.currencyconverter.model.DailyResponse
import com.magednan.currencyconverter.model.HistoryItem
import com.magednan.currencyconverter.model.RateResponse
import com.magednan.currencyconverter.network.CurrencyApi
import com.magednan.currencyconverter.utils.Resource
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import javax.inject.Inject

class Repository @Inject constructor(
    val dao: HistoryDao,
    val api: CurrencyApi
) : RepositoryInterface {

    /*
    because api does not provide convertCurrencies for free so
    i use latest rate and calculate manual
     */
    override suspend fun convertCurrencies(
        apiKey: String,
        baseCurrency: String,
        symbols: String
    ): Resource<RateResponse> {
        return try {
            val response = api.convertCurrencies(apiKey, baseCurrency, symbols)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }

    //to provide rates for other Currencies to our base cuurency
    override suspend fun getCurrenciesRates(
        apiKey: String,
        baseCurrency: String
    ): Resource<RateResponse> {
        return try {
            val response = api.getCurrenciesRates(apiKey, baseCurrency)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }

    //getting dates for last 30days
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(): ArrayList<String> {
        var date = LocalDate.now()
        val dateArr = ArrayList<String>()
        for (i in 0..29) {
            var dateStr = date.toString()
            dateArr.add(dateStr)
            date = date.minusDays(1)
        }
        return dateArr
    }
    //to comper for other Currencies to our base currency daily
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDailyCurrenciesRates(
        apiKey: String,
        baseCurrency: String,
        symbols: String
    ): Resource<List<DailyResponse>> {
        getDate()
        val dailyList = ArrayList<DailyResponse>()
        for (i in 0..getDate().size - 1) {
            var response =
                api.getDailyCurrenciesRates(getDate().get(i), apiKey, baseCurrency, symbols)
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("TAG", "getDailyCurrenciesRates: "+response.body()!!.date)
                    dailyList.add(it)
                }
            }
        }
        return if (dailyList.size >= 1) {
            Resource.success(dailyList)
        } else Resource.error("No data!", null)
    }


    //update &  insert items in db
    override suspend fun upsert(historyItem: HistoryItem) {
        dao.upsert(historyItem)
    }
    //get all items in db
    override fun getHistory(): LiveData<List<HistoryItem>> {
        return dao.getHistory()
    }
    //delete item in db
    override suspend fun deleteHistory(historyItem: HistoryItem) {
        dao.deleteHistory(historyItem)
    }
}
