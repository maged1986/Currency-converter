package com.magednan.currencyconverter.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.magednan.currencyconverter.R
import com.magednan.currencyconverter.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<DetailsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        ViewActions.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun clickDetailsButton_navigateToDetailsFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.home_btn_details)).perform(click())

        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment()
        )
    }
    @Test
    fun clickHistoryButton_navigateToHistoryFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.home_btn_history)).perform(click())

        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToHistoryFragment()
        )
    }

}