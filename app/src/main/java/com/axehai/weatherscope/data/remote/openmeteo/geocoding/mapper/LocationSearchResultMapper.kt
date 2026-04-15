package com.axehai.weatherscope.data.remote.openmeteo.geocoding.mapper

import com.axehai.weatherscope.data.remote.openmeteo.geocoding.dto.GeocodingResultDto
import com.axehai.weatherscope.domain.model.LocationSearchResult

fun GeocodingResultDto.toLocationSearchResult() = LocationSearchResult(
    name = name,
    country = country,
    admin1 = admin1,
    latitude = latitude,
    longitude = longitude,
)
