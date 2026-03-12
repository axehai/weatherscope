package com.axehai.weatherscope.domain.repository

import com.axehai.weatherscope.domain.model.ActiveLocation

/**
 * Repository interface for managing the active location in the weather scope application.
 * This interface defines operations to retrieve and update the currently active location.
 */
interface ActiveLocationRepository {
	/**
	 * Retrieves the currently active location.
	 *
	 * @return The [ActiveLocation] object representing the active location.
	 */
	suspend fun getActiveLocation(): ActiveLocation

	/**
	 * Sets the specified location as the active location.
	 *
	 * @param location The [ActiveLocation] to set as active.
	 */
	suspend fun setActiveLocation(location: ActiveLocation)
}