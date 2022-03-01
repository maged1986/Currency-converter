package com.magednan.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.magednan.currencyconverter.databinding.HistoryListItemBinding
import com.magednan.currencyconverter.model.HistoryItem

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

    //viewHolder for history rates
    inner class HistoryHolder(val binding: HistoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    //differCallback to check for update
    private val differCallback = object : DiffUtil.ItemCallback<HistoryItem>() {
        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }

    }
    //AsyncListDiffer to do check on worker thread

    val differ = AsyncListDiffer(this, differCallback)

    //inflating the holder using dataBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val binding =
            HistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(binding)
    }

    //Binding data in the holder
    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val historyItem = differ.currentList[position]
        holder.binding.apply {
            historyTvAmount.text = "The amount:  " + historyItem.amount.toString()
            historyTvResult.text = "The result:  " + historyItem.result.toString()
            historyTvFrom.text = "The base currency:  " + historyItem.from.toString()
            historyTvTo.text = "The converted currency:  " + historyItem.to.toString()

        }
    }

    //getting the size of list
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}