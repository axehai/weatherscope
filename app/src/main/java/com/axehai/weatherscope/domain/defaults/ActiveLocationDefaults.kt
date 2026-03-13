package com.axehai.weatherscope.domain.defaults

import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.model.LocationSource

object ActiveLocationDefaults {
	val NEW_DELHI= ActiveLocation(
		name = "New Delhi",
		latitude = 28.62137,
		longitude = 77.2148,
		source = LocationSource.DEFAULT,
		country = "India"
	)
}