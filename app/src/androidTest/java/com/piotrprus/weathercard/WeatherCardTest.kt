package com.piotrprus.weathercard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class WeatherCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkWeatherCardExists() {
        composeTestRule.setContent {
            val selected = remember { mutableStateOf(0) }
            WeatherCard(list = forecastMockState, selectedValue = selected.value, onValueChange = { selected.value = it })
        }
        composeTestRule.onNodeWithTag(WEATHER_CARD_TAG).assertExists()
    }

    @Test
    fun checkFirstElementDataIsDisplayed() {
        composeTestRule.setContent {
            val selected = remember { mutableStateOf(0) }
            WeatherCard(list = forecastMockState, selectedValue = selected.value, onValueChange = { selected.value = it })
        }
        composeTestRule.onNodeWithTag(TEMPERATURE_TAG).assertTextEquals(forecastMockState.first().temperature.toString())
        composeTestRule.onNodeWithTag(DATE_TAG).assertTextEquals(forecastMockState.first().date)
        composeTestRule.onNodeWithTag(SLIDER_TAG).assertIsEnabled()
    }

    @Test
    fun changeSliderPositionAndAssertTheDisplayElement() {
        composeTestRule.setContent {
            val selected = remember { mutableStateOf(0) }
            WeatherCard(list = forecastMockState, selectedValue = selected.value, onValueChange = { selected.value = it })
        }
        val rect = composeTestRule.onNodeWithTag(SLIDER_TAG).getBoundsInRoot()
        val widthPx = with(composeTestRule.density) { rect.right.minus(rect.left).toPx() }
        val distanceToPointThree = widthPx.div(forecastMockState.size).times(3)
        composeTestRule.onNodeWithTag(SLIDER_TAG).performTouchInput { swipeRight(0f, distanceToPointThree) }
        composeTestRule.onNodeWithTag(TEMPERATURE_TAG).assertTextEquals(forecastMockState[2].temperature.toString())
    }
}