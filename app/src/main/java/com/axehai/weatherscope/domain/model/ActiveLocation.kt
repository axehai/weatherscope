package com.axehai.weatherscope.domain.model

data class ActiveLocation(
	val latitude: Double,
	val longitude: Double,
	val name:String,
	val source: LocationSource,
)

enum class LocationSource{
	DEFAULT,
	DEVICE,
	SEARCH
}