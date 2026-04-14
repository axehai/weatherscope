package com.axehai.weatherscope.data.remote.openmeteo.geocoding.mapper

import com.axehai.weatherscope.data.remote.openmeteo.geocoding.dto.GeocodingResultDto
import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.model.LocationSearchResult
import com.axehai.weatherscope.domain.model.LocationSource

fun GeocodingResultDto.toLocationSearchResult() = LocationSearchResult(
    name = name,
    country = country,
    admin1 = admin1,
    latitude = latitude,
    longitude = longitude,
)

fun LocationSearchResult.toActiveLocation() = ActiveLocation(
    latitude = latitude,
    longitude = longitude,
    name = name,
    country = country,
    source = LocationSource.SEARCH
)
