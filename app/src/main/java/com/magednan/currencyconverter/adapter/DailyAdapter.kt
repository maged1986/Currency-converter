package com.magednan.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.magednan.currencyconverter.databinding.DailyListItemBinding
import com.magednan.currencyconverter.databinding.RatesListItemBinding
import com.magednan.currencyconverter.model.CurrencyRate
import com.magednan.currencyconverter.model.DailyItem

class DailyAdapter : RecyclerView.Adapter<DailyAdapter.DailyHolder>() {
    //differCallback to check for update
    private val differCallback = object : DiffUtil.ItemCallback<DailyItem>() {
        //i use date because i want to get
        override fun areItemsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem == newItem
        }

    }

    //AsyncListDiffer to do check on worker thread
    val differ = AsyncListDiffer(this, differCallback)

    //viewHolder for daily rates
    inner class DailyHolder(val binding: DailyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    //inflating the holder using dataBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        val binding =
            DailyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyHolder(binding)
    }

    //Binding data in the holder
    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
        val dailyItem = differ.currentList[position]
        holder.binding.apply {
            dailyTvDate.text ="the date is :" +dailyItem.date
            dailyTvRate.text = "the rate is :" +dailyItem.rate.toString()
            dailyTvTag.text = "the currency tag is :"+dailyItem.tag
        }
    }

    //getting the size of list
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}