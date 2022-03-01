package com.magednan.currencyconverter.historyDB

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {
    //to provideDB and inject room db in app
    @Provides
    @Singleton
    fun provideDB(application: Application?) =
        Room.databaseBuilder(application!!, HistoryDB::class.java, "historyDB")
            .allowMainThreadQueries()
            .build()

    //to provideDB and inject room dao in app
    @Provides
    @Singleton
    fun provideYourDao(db: HistoryDB) = db.getHistoryDao()
}