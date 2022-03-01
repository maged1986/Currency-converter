package com.magednan.currencyconverter.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.currencyconverter.R
import com.magednan.currencyconverter.adapter.HistoryAdapter
import com.magednan.currencyconverter.databinding.FragmentHistoryBinding
import com.magednan.currencyconverter.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    //vars
    private val viewModel by viewModels<ViewModel>()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // calling the methods
        setupRecyclerView()
    }

    /*
       * inflating and setupRecyclerView
       */
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.historyFragRv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            historyAdapter = HistoryAdapter()
            adapter = historyAdapter
            lifecycleScope.launch {
                viewModel.getHistory()
                subscribeToObservers()
            }
        }
    }

   //calling methods in view model and giving it therequired data
    private fun subscribeToObservers() {
        viewModel.getHistory().observe(viewLifecycleOwner, Observer {
            historyAdapter.differ.submitList(it)
        })
    }


}