package com.magednan.currencyconverter.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.magednan.currencyconverter.databinding.FragmentHomeBinding
import com.magednan.currencyconverter.viewmodel.ViewModel

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.magednan.currencyconverter.R
import com.magednan.currencyconverter.model.HistoryItem
import com.magednan.currencyconverter.model.RateResponse
import com.magednan.currencyconverter.utils.Constants.API_KEY
import com.magednan.currencyconverter.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    //vars
    private val viewModel by viewModels<ViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var fromAdapter: ArrayAdapter<String>
    private lateinit var toAdapter: ArrayAdapter<String>
    private var fromCurrency = ""
    private var toCurrency = ""
    lateinit var rateResponse:RateResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // calling the methods
        getBaseCurrency()
        getConvertedCurrency()
        getData()
        subscribeToObservers()

        //handling SwipeCurr button
        binding.homeBtnSwipeCurr.setOnClickListener {
            swipeCurrenices(fromCurrency, toCurrency)
        }

        //handling Calculate Value button
        binding.homeBtnCalValue.setOnClickListener {
            if (rateResponse.success ==true) {
                    binding.homeTvValue.text = calculateTheValue(toCurrency).toString()
                    lifecycleScope.launch {
                        val amount = binding.homeEtAmount.text.toString().toDouble()
                        viewModel.upsert(
                            HistoryItem(
                                from = fromCurrency,
                                to = toCurrency,
                                amount = amount,
                                result = calculateTheValue(fromCurrency)
                            )
                        )
                    }
                } else {
                    Toast.makeText(context, "There is a net work conncetion problem", Toast.LENGTH_LONG).show()
            }
        }

        //handling details fragment navigation  button
        binding.homeBtnDetails.setOnClickListener {
            val bundle = Bundle().apply {
                putString("base", getBaseCurrency())
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment, bundle
            )
        }

        //handling details fragment navigation  button
        binding.homeBtnHistory.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_historyFragment
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.rate.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    rateResponse = it.data!!
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                }

                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()

                }
            }
        })
    }

    //getting BaseCurrency from spinner
    private fun getBaseCurrency(): String {
        //inflate the ArrayAdapter
        fromAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            requireContext().resources.getStringArray(R.array.currencies)
        )
        // Specify the layout to use when the list of choices appears
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.homeSpnBaseCurr.adapter = fromAdapter

        //adding temSelectedListener to the adapter
        binding.homeSpnBaseCurr.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    fromCurrency =
                        binding.homeSpnBaseCurr.getSelectedItem().toString().substring(0, 3)
                    getData()
                    subscribeToObservers()
                    Toast.makeText(context,"you changed curriecy from" +fromCurrency, Toast.LENGTH_LONG).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        return fromCurrency
    }

    //getting ConvertedCurrency from spinner
    private fun getConvertedCurrency(): String {
        //inflate the ArrayAdapter
        toAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            requireContext().resources.getStringArray(R.array.currencies)
        )
        // Specify the layout to use when the list of choices appears
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.homeSpnConvCurr.adapter = toAdapter

        //adding temSelectedListener to the adapter
        binding.homeSpnConvCurr.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    toCurrency =
                        binding.homeSpnConvCurr.getSelectedItem().toString().substring(0, 3)
                    getData()
                    subscribeToObservers()
                    Toast.makeText(context, "you changed curriecy to" +toCurrency, Toast.LENGTH_LONG).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        return toCurrency
    }

    //swipeCurrenices
    private fun swipeCurrenices(from: String, to: String) {
        val toSpinnerPosition: Int = toAdapter.getPosition(to)
        binding.homeSpnBaseCurr.setSelection(toSpinnerPosition)
        val fromSpinnerPosition: Int = fromAdapter.getPosition(from)
        binding.homeSpnConvCurr.setSelection(fromSpinnerPosition)
    }

    //calculateTheValue using api rates
    private fun calculateTheValue(from: String): Double {
        var rate = 0.0
        val amount = binding.homeEtAmount.text.toString().toDouble()
        rate = rateResponse.rates!!.get(toCurrency)!!
        return rate * amount

    }

    //calling methods in view model and giving it the required data
    private fun getData() {
        lifecycleScope.launch {
            viewModel.convertCurrencies(API_KEY, fromCurrency, toCurrency)
        }
    }
}



