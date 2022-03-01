package com.magednan.currencyconverter.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.currencyconverter.R
import com.magednan.currencyconverter.adapter.DailyAdapter
import com.magednan.currencyconverter.adapter.RatesAdapter
import com.magednan.currencyconverter.databinding.FragmentDetailsBinding
import com.magednan.currencyconverter.model.CurrencyRate
import com.magednan.currencyconverter.model.DailyItem
import com.magednan.currencyconverter.utils.Constants
import com.magednan.currencyconverter.utils.Constants.API_KEY
import com.magednan.currencyconverter.utils.Status
import com.magednan.currencyconverter.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    //vars
    private lateinit var binding: FragmentDetailsBinding
    val args: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<ViewModel>()
    private lateinit var ratesAdapter: RatesAdapter
    private lateinit var dailyAdapter: DailyAdapter
    private lateinit var baseCurrency: String
    val list = ArrayList<CurrencyRate>()
    val dailyListItems = ArrayList<DailyItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getting baseCurrency through navargs
        baseCurrency = args.base
        binding.detailsFragTvBaseCurr.text = "Base Currency is:" + baseCurrency
        // calling the methods
        getData()
        setupRatesRecyclerView()
        setupDailyRecyclerView()
        subscribeToObservers()
    }

    /*
    * inflating and setupRatesRecyclerView
    */
    private fun setupRatesRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.detailsFragRatsRv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            ratesAdapter = RatesAdapter()
            adapter = ratesAdapter

        }
    }

    /*
  * inflating and setupDailysRecyclerView
  */
    private fun setupDailyRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.detailsFragDailyRv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            dailyAdapter = DailyAdapter()
            adapter = dailyAdapter

        }
    }

    //getting updated data from view model
    private fun subscribeToObservers() {
        //getting updated rates from view model using resource class to handle data
        viewModel.allRates.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.success!! == true) {
                        it.data?.rates?.forEach { (key, value) ->
                            list.add(CurrencyRate(key, value))
                            ratesAdapter.differ.submitList(list)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "There is a network conncetion problem",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "There is a network conncetion", Toast.LENGTH_LONG)
                        .show()
                }

                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()

                }
            }
        })

        //getting updated daily rates from view model using resource class to handle data
        viewModel.dailyRates.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.size!! >= 1) {
                        for (i in 0..it.data?.size!! - 1) {
                            var dailyResponse = it.data.get(i)
                            dailyResponse.rates?.forEach { (key, value) ->
                                dailyListItems.add(DailyItem(dailyResponse.date, key, value))
                                Log.d("TAG", "subscribeToObservers: " + key)
                                dailyAdapter.differ.submitList(dailyListItems)

                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "There is a net work conncetion problem",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message ?: "here is a net work",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()

                }
            }
        })

    }

    //calling methods in view model and giving it the required data
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData() {
        lifecycleScope.launch {
            viewModel.getCurrenciesRates(Constants.API_KEY, baseCurrency)
            viewModel.getDailyCurrenciesRates(API_KEY, baseCurrency, " GBP,JPY,EUR,ZWL")
        }

    }


}





