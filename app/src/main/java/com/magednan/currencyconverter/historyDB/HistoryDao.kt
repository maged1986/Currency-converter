package com.magednan.currencyconverter.historyDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.magednan.currencyconverter.model.HistoryItem

/**
 * The Data Access Object for the [HistoryItem] class.
 */
@Dao
interface HistoryDao {
    //update &  insert items in db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(historyItem: HistoryItem)

    //get all items in db
    @Query("SELECT * FROM history")
    fun getHistory(): LiveData<List<HistoryItem>>
   //delete item in db
    @Delete
    suspend fun deleteHistory(historyItem: HistoryItem)
}