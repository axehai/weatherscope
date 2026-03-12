package com.axehai.weatherscope.data.local.mapper

import com.axehai.weatherscope.data.local.model.StoredActiveLocation
import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.model.LocationSource

fun ActiveLocation.toStored(): StoredActiveLocation =
	StoredActiveLocation(
		latitude = latitude,
		longitude = longitude,
		name = name,
		sourceId = source.sourceId
	)

fun StoredActiveLocation.toDomain(): ActiveLocation =
	ActiveLocation(
		latitude = latitude,
		longitude = longitude,
		name = name,
		source = LocationSource.fromSourceId(sourceId)
	)