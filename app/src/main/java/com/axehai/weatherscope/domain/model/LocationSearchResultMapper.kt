package com.axehai.weatherscope.domain.model

fun LocationSearchResult.toActiveLocation() = ActiveLocation(
    latitude = latitude,
    longitude = longitude,
    name = name,
    country = country,
    source = LocationSource.SEARCH
)
