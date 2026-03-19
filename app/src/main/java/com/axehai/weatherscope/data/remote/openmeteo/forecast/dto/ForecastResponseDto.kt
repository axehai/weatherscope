package com.axehai.weatherscope.data.remote.openmeteo.forecast.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String? = null,
    @SerialName("timezone_abbreviation") val timezoneAbbreviation: String? = null,
    @SerialName("utc_offset_seconds") val utcOffsetSeconds: Int? = null,
    val current: CurrentWeatherDto? = null,
    val daily: DailyWeatherDto? = null
)

@Serializable
data class CurrentWeatherDto(
    val time: String? = null,
    @SerialName("temperature_2m") val temperature2m: Double? = null,
    @SerialName("relative_humidity_2m") val relativeHumidity2m: Int? = null,
    @SerialName("apparent_temperature") val apparentTemperature: Double? = null,
    @SerialName("is_day") val isDay: Int? = null,
    val precipitation: Double? = null,
    @SerialName("weather_code") val weatherCode: Int? = null,
    @SerialName("wind_speed_10m") val windSpeed10m: Double? = null
)

@Serializable
data class DailyWeatherDto(
    val time: List<String> = emptyList(),
    @SerialName("weather_code") val weatherCode: List<Int?> = emptyList(),
    @SerialName("temperature_2m_max") val temperature2mMax: List<Double?> = emptyList(),
    @SerialName("temperature_2m_min") val temperature2mMin: List<Double?> = emptyList(),
    @SerialName("precipitation_probability_max") val precipitationProbabilityMax: List<Int?> = emptyList(),
    val sunrise: List<String?> = emptyList(),
    val sunset: List<String?> = emptyList()
)