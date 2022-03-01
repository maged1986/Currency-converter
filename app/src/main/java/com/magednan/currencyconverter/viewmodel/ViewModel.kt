package com.magednan.currencyconverter.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.magednan.currencyconverter.model.DailyResponse
import com.magednan.currencyconverter.model.HistoryItem
import com.magednan.currencyconverter.model.RateResponse
import com.magednan.currencyconverter.repository.Repository
import com.magednan.currencyconverter.repository.RepositoryInterface
import com.magednan.currencyconverter.utils.Constants
import com.magednan.currencyconverter.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ViewModel @Inject constructor(
    val repository: RepositoryInterface
) : ViewModel() {
    //rate live data for converting the currency
    private val _rate = MutableLiveData<Resource<RateResponse>>()
    val rate: LiveData<Resource<RateResponse>>
        get() = _rate
    //allRates live data for other currency rates
    private val _allRates = MutableLiveData<Resource<RateResponse>>()
    val allRates: LiveData<Resource<RateResponse>>
        get() = _allRates

    //dailyRates live data for other currency rates daily
    private val _dailyRates = MutableLiveData<Resource<List<DailyResponse>>>()
    val dailyRates: LiveData<Resource<List<DailyResponse>>>
        get() = _dailyRates

    /*
   because api does not provide convertCurrencies for free so
   i use latest rate and calculate manual
    */
    suspend fun convertCurrencies(
        apiKey: String,
        baseCurrency: String,
        symbols: String
    ) = viewModelScope.launch {
        if (apiKey.isEmpty() || baseCurrency.isEmpty() || symbols.isEmpty() ) {
            _rate.postValue(Resource.error("There is an error", null))
        }else{
            _rate.value = repository.convertCurrencies(apiKey, baseCurrency, symbols)
        }

    }

    //to provide rates for other Currencies to our base cuurency
    suspend fun getCurrenciesRates(apiKey: String, baseCurrency: String
    ) = viewModelScope.launch {
        if (apiKey.isEmpty() || baseCurrency.isEmpty()  ) {
            _allRates.postValue(Resource.error("There is an error", null))
        }else{
            _allRates.value = repository.getCurrenciesRates(apiKey, baseCurrency)
        }
    }

    //to comper for other Currencies to our base currency daily
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDailyCurrenciesRates(apiKey: String,
                                        baseCurrency: String,
                                        symbols: String) =
        viewModelScope.launch {
            if (apiKey.isEmpty() || baseCurrency.isEmpty() || symbols.isEmpty() ) {
                _dailyRates.postValue(Resource.error("There is an error", null))
            }else{
                _dailyRates.value = repository.getDailyCurrenciesRates(apiKey, baseCurrency,symbols)
            }
    }

    //update &  insert items in db
    suspend fun upsert(historyItem: HistoryItem) = viewModelScope.launch {
        repository.upsert(historyItem)
    }

    //get all items in db
    fun getHistory(): LiveData<List<HistoryItem>> {
        return repository.getHistory()
    }

    //delete item in db
    suspend fun deleteHistory(historyItem: HistoryItem) = viewModelScope.launch {
        repository.deleteHistory(historyItem)
    }
}