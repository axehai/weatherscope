package com.axehai.weatherscope.data.remote.openmeteo.geocoding.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponseDto(
    val results: List<GeocodingResultDto>? = null,
    @SerialName("generationtime_ms")
    val generationTimeMs: Double? = null
)

@Serializable
data class GeocodingResultDto(
    val id: Int? = null,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    val admin1: String? = null,
    val admin2: String? = null,
    val admin3: String? = null,
    val admin4: String? = null,
    val timezone: String? = null
)