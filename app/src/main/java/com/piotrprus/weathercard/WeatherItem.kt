package com.piotrprus.weathercard

data class WeatherItem(
    val date: String,
    val temperature: Int,
    val precipitation: Int,
    val windSpeed: Int
)
