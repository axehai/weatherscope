package com.axehai.weatherscope.data.local.model

import kotlinx.serialization.Serializable


@Serializable
data class StoredActiveLocation(
	val latitude: Double = 0.0,
	val longitude: Double = 0.0,
	val name: String = "",
	val country: String? = null,
	val sourceId: Int = SOURCE_UNINITIALIZED,
) {
	companion object {
		const val SOURCE_UNINITIALIZED = -1
	}
}

