package com.magednan.currencyconverter.repository

import com.magednan.currencyconverter.historyDB.HistoryDao
import com.magednan.currencyconverter.network.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //to provide and inject Repository as RepositoryInterface in app
    @Singleton
    @Provides
    fun injectNormalRepo(dao : HistoryDao, api: CurrencyApi) = Repository(dao,api) as RepositoryInterface
}