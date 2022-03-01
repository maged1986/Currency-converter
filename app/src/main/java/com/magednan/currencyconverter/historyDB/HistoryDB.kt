package com.magednan.currencyconverter.historyDB

import android.icu.util.Currency
import androidx.room.Database
import androidx.room.RoomDatabase
import com.magednan.currencyconverter.model.HistoryItem


@Database(
        entities = [HistoryItem::class],
        version = 1, exportSchema = false
    )
    abstract class HistoryDB : RoomDatabase() {
        abstract fun getHistoryDao(): HistoryDao
}