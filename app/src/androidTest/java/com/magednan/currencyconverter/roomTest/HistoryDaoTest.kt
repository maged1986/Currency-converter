package com.magednan.currencyconverter.roomTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.magednan.currencyconverter.getOrAwaitValue
import com.magednan.currencyconverter.historyDB.HistoryDB
import com.magednan.currencyconverter.historyDB.HistoryDao
import com.magednan.currencyconverter.model.HistoryItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.internal.matchers.Contains
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class HistoryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database : HistoryDB

    private lateinit var dao : HistoryDao

    @Before
    fun setup() {

        hiltRule.inject()
        dao = database.getHistoryDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertItemTesting() = runBlockingTest {
        val item = HistoryItem(20,"EUR","USD",10.0,11.0)
        dao.upsert(item)

        val list = dao.getHistory().getOrAwaitValue()
        assertThat(list).contains(item)

    }

    @Test
    fun deleteItemTesting() = runBlockingTest {
        val item = HistoryItem(20,"EUR","USD",10.0,11.0)
        dao.upsert(item)
        dao.deleteHistory(item)

        val list = dao.getHistory().getOrAwaitValue()
        assertThat(list).doesNotContain(item)

    }
    @Test
    fun ContainsItemTesting() = runBlockingTest {
        val item = HistoryItem(20,"EUR","USD",10.0,11.0)
        dao.upsert(item)

        val list = dao.getHistory().getOrAwaitValue()
        assertThat(list).contains(item)

    }
}