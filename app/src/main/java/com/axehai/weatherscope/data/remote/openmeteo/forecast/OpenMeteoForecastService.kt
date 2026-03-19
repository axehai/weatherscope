package com.axehai.weatherscope.data.remote.openmeteo.forecast

import com.axehai.weatherscope.data.remote.openmeteo.forecast.dto.ForecastResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class OpenMeteoForecastService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        currentFields: String = DEFAULT_CURRENT_FIELDS,
        dailyFields: String = DEFAULT_DAILY_FIELDS,
        timezone: String = "auto",
        forecastDays: Int = 7
    ): ForecastResponseDto {
        return client.get(FORECAST_URL) {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("current", currentFields)
            parameter("daily", dailyFields)
            parameter("timezone", timezone)
            parameter("forecast_days", forecastDays)
        }.body()
    }

    private companion object {
        const val FORECAST_URL = "https://api.open-meteo.com/v1/forecast"

        const val DEFAULT_CURRENT_FIELDS =
            "temperature_2m," + "relative_humidity_2m," + "apparent_temperature," + "is_day," + "precipitation," + "weather_code," + "wind_speed_10m"

        const val DEFAULT_DAILY_FIELDS =
            "weather_code," + "temperature_2m_max," + "temperature_2m_min," + "precipitation_probability_max," + "sunrise," + "sunset"
    }
}