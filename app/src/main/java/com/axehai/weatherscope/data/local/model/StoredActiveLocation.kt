package com.axehai.weatherscope.data.local.model


data class StoredActiveLocation(
	val latitude: Double,
	val longitude: Double,
	val name: String,
	val country: String?,
	val sourceId: Int,
)