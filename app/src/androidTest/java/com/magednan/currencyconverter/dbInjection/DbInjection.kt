package com.magednan.currencyconverter.dbInjection

import android.content.Context
import androidx.room.Room
import com.magednan.currencyconverter.historyDB.HistoryDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DbInjection {
    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,HistoryDB::class.java)
            .allowMainThreadQueries()
            .build()
}