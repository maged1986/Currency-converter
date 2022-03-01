package com.magednan.currencyconverter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import com.magednan.currencyconverter.MainCoroutineRule
import com.magednan.currencyconverter.getOrAwaitValueTest
import com.magednan.currencyconverter.model.HistoryItem
import com.magednan.currencyconverter.repository.FakeRepository
import com.magednan.currencyconverter.utils.Status
import kotlinx.coroutines.runBlocking
import org.junit.Test


@RunWith(AndroidJUnit4::class)
class ViewModelTest : TestCase(){
 /*I know that viewmodel test class must be in test
 * but after android 12 Sdk 31 google change it
 * so i made two once in test and another in android test
 * */

 /*private lateinit var viewModel:ViewModel

 @get:Rule
 var instantTaskExecutorRule = InstantTaskExecutorRule()



 @get:Rule
 var mainCoroutineRule = MainCoroutineRule()
 @Before
 override fun setUp() {
  viewModel= ViewModel(FakeRepository())
 }
 @Test
 fun getCurrenciesRatesSuccess() {
  runBlocking {
   viewModel.getCurrenciesRates("nkkj","Usd")

   val value = viewModel.allRates.getOrAwaitValueTest()
   assertThat(value.status).isEqualTo(Status.ERROR)
  }
 }
 @Test
 fun getCurrenciesRatesFail() {
  runBlocking {
   viewModel.getCurrenciesRates("nkkj","")

   val value = viewModel.allRates.getOrAwaitValueTest()
   assertThat(value.status).isEqualTo(Status.ERROR)
  }
 }
 @Test
 fun getDailyCurrenciesRatesSuccess() {
  runBlocking {
   viewModel.getDailyCurrenciesRates("nkkj","Usd","GBP")

   val value = viewModel.dailyRates.getOrAwaitValueTest()
   assertThat(value.status).isEqualTo(Status.ERROR)
  }
 }
 @Test
 fun getDailyCurrenciesRatesFail() {
  runBlocking {
   viewModel.convertCurrencies("nkkj","","")

   val value = viewModel.dailyRates.getOrAwaitValueTest()
   assertThat(value.status).isEqualTo(Status.ERROR)
  }
 }
 @Test
 fun upsertItemSuccess() {
  runBlocking {
   val item= HistoryItem(20,"USD","EUR",3.5,35.0)
   viewModel.upsert(item)

   val value = viewModel.getHistory().getOrAwaitValueTest()
   assertThat(value).contains(item)
  }
 }
 @Test
 fun upsertItemFail() {
  runBlocking {
   val item= HistoryItem(20,"USD","EUR",3.5,35.0)
   viewModel.upsert(item)
   val value = viewModel.getHistory().getOrAwaitValueTest()
   assertThat(value).doesNotContain(item)
  }
 }*/
}