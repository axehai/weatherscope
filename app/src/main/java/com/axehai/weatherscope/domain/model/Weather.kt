package com.axehai.weatherscope.domain.model

import java.time.LocalDate

data class CurrentWeather(
	val date: LocalDate,
	val currentTemperatureC: Double,
	val minTempC: Double,
	val maxTempC: Double,
	val weatherCode: Int,
	val conditionLabel: String,
)

data class TodaysHighlights(
	val feelsLikeC: Double,
	val rainPercent: Double,
	val humidityPercent: Double,
	val windSpeedKph: Double,
	val uvIndex: Double,
	val pressure: Double,
)

data class DailyForecast(
	val date: LocalDate,
	val minTempC: Double,
	val maxTempC: Double,
	val rainProbability: Double,
	val weatherCode: Int
)
