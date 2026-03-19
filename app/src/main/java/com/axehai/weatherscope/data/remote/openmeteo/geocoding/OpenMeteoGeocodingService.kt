package com.axehai.weatherscope.data.remote.openmeteo.geocoding

import com.axehai.weatherscope.data.remote.openmeteo.geocoding.dto.GeocodingResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class OpenMeteoGeocodingService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun searchLocations(
        query: String,
        count: Int = DEFAULT_COUNT,
        language: String = DEFAULT_LANGUAGE,
        format: String = DEFAULT_FORMAT
    ): GeocodingResponseDto {
        return client.get(GEOCODING_URL) {
            parameter("name", query)
            parameter("count", count)
            parameter("language", language)
            parameter("format", format)
        }.body()
    }

    companion object {
        private const val GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search"

        private const val DEFAULT_COUNT = 10
        private const val DEFAULT_LANGUAGE = "en"
        private const val DEFAULT_FORMAT = "json"
    }
}
