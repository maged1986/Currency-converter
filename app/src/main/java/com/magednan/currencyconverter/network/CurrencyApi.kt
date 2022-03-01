package com.magednan.currencyconverter.network

import com.magednan.currencyconverter.model.DailyResponse
import com.magednan.currencyconverter.model.RateResponse
import com.magednan.currencyconverter.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {
    @GET("api/latest")
    suspend fun convertCurrencies(
        @Query("access_key") apiKey:String= API_KEY,
        @Query(" base ") baseCurrency:String,
        @Query("symbols") symbols: String )
            : Response<RateResponse>

    @GET("api/latest")
    suspend fun getCurrenciesRates(
        @Query("access_key") apiKey:String= API_KEY,
        @Query(" base ") baseCurrency:String,
        ): Response<RateResponse>

    @GET("api/{date}")
    suspend fun getDailyCurrenciesRates(
        @Path("date") date: String,
        @Query("access_key") apiKey:String,
        @Query(" base ") baseCurrency:String,
        @Query(" symbols ") symbols:String
    ): Response<DailyResponse>

}