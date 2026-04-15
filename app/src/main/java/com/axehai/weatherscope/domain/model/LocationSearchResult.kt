package com.axehai.weatherscope.domain.model

data class LocationSearchResult(
    val name: String,
    val country: String?,
    val admin1: String?,
    val latitude: Double,
    val longitude: Double,
)
