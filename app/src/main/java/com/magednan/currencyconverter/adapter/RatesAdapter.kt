package com.magednan.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.magednan.currencyconverter.databinding.RatesListItemBinding
import com.magednan.currencyconverter.model.CurrencyRate

class RatesAdapter : RecyclerView.Adapter<RatesAdapter.RatesHolder>() {

    //differCallback to check for update
    private val differCallback = object : DiffUtil.ItemCallback<CurrencyRate>() {
        override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem == newItem
        }
    }

    //AsyncListDiffer to do check on worker thread
    val differ = AsyncListDiffer(this, differCallback)

    //viewHolder for rates
    inner class RatesHolder(val binding: RatesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    //inflating the holder using dataBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesHolder {
        val binding =
            RatesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatesHolder(binding)
    }

    //Binding data in the holder
    override fun onBindViewHolder(holder: RatesHolder, position: Int) {
        val currencyRate = differ.currentList[position]
        holder.binding.apply {
            ratesTvCurrencyTag.text = "the currency tag is :"+currencyRate.tag.toString()
            ratesTvCurrencyValue.text ="the rate is :"+ currencyRate.value.toString()
        }
    }

    //getting the size of list
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}