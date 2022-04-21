package com.piotrprus.weathercard

import androidx.compose.runtime.mutableStateListOf

val forecastMockState = mutableStateListOf(
    WeatherItem(date = "Mon, 8:00 AM, Cloudy", temperature = 12, 30, 2, R.drawable.cloudy),
    WeatherItem(date = "Mon, 9:00 AM, Cloudy", temperature = 14, 60, 3, R.drawable.cloudy),
    WeatherItem(date = "Mon, 10:00 AM, Mostly cloudy", temperature = 15, 80, 5, R.drawable.rain),
    WeatherItem(date = "Mon, 11:00 AM, Mostly sunny", temperature = 15, 30, 2, R.drawable.sunny),
    WeatherItem(date = "Mon, 12:00 PM, Mostly sunny", temperature = 18, 20, 1, R.drawable.sunny),
    WeatherItem(date = "Mon, 1:00 PM, Sunny", temperature = 20, 0, 1, R.drawable.sunny),
    WeatherItem(date = "Mon, 2:00 PM, Sunny", temperature = 20, 0, 2, R.drawable.sunny),
    WeatherItem(date = "Mon, 3:00 PM, Sunny", temperature = 19, 0, 2, R.drawable.sunny),
    WeatherItem(date = "Mon, 4:00 PM, Sunny", temperature = 19, 0, 2, R.drawable.sunny),
    WeatherItem(date = "Mon, 5:00 PM, Sunny", temperature = 17, 0, 3, R.drawable.sunny),
    WeatherItem(date = "Mon, 6:00 PM, Heavy showers", temperature = 16, 80, 7, R.drawable.rain),
    WeatherItem(date = "Mon, 7:00 PM, Heavy showers", temperature = 15, 90, 9, R.drawable.rain),
)

const val WEATHER_CARD_TAG = "WeatherCardTag"
const val TEMPERATURE_TAG = "TemperatureTag"
const val DATE_TAG  = "DateTag"
const val SLIDER_TAG = "SliderTag"