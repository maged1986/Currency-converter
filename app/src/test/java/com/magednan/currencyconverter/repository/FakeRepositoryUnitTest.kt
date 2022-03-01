package com.magednan.currencyconverter.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.magednan.currencyconverter.model.DailyResponse
import com.magednan.currencyconverter.model.HistoryItem
import com.magednan.currencyconverter.model.RateResponse
import com.magednan.currencyconverter.utils.Resource
import org.junit.Before

class FakeRepository : RepositoryInterface {

    private val  historyListItem=mutableListOf<HistoryItem>()
    private val  dailylist= arrayListOf<DailyResponse>()
    private val historyListItemLive= MutableLiveData<List<HistoryItem>>(historyListItem)
    private var map=HashMap<String,Double>()

    @Before
    fun setUp(){
        map.put("USD",3.3)
        map.put("GBP",3.3)
        map.put("SAR",3.3)
        dailylist.add(DailyResponse("USD","20-12-2021", true,map,true,1500))
        dailylist.add(DailyResponse("USD","20-12-2021", true,map,true,1500))
        dailylist.add(DailyResponse("USD","20-12-2021", true,map,true,1500))
        dailylist.add(DailyResponse("USD","20-12-2021", true,map,true,1500))

    }

    override suspend fun convertCurrencies(
        apiKey: String,
        baseCurrency: String,
        symbols: String
    ): Resource<RateResponse> {
        return Resource.success(RateResponse("USD","28/2/2022",map,true,"1500"))
    }

    override suspend fun getCurrenciesRates(
        apiKey: String,
        baseCurrency: String
    ): Resource<RateResponse> {
        return Resource.success(RateResponse("USD","28/2/2022",map,true,"1500"))
    }

    override suspend fun getDailyCurrenciesRates(
        apiKey: String,
        baseCurrency: String,
        symbols: String
    ): Resource<List<DailyResponse>> {
        return Resource.success(dailylist)
    }


    override suspend fun upsert(historyItem: HistoryItem) {
        historyListItem.add(historyItem)
        refreshLiveData()
    }



    override fun getHistory(): LiveData<List<HistoryItem>> {
        return historyListItemLive

    }

    override suspend fun deleteHistory(historyItem: HistoryItem) {
        historyListItem.clear()
        refreshLiveData()
    }

    private fun refreshLiveData() {
        historyListItemLive.value=historyListItem
    }
}