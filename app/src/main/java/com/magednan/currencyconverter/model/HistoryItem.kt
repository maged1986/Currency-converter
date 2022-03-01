package com.magednan.currencyconverter.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryItem(
  @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val from:String,
    val to :String,
    val amount :Double,
    val result:Double
)
