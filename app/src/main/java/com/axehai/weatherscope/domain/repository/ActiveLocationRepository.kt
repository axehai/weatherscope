package com.axehai.weatherscope.domain.repository

import com.axehai.weatherscope.domain.model.ActiveLocation
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing the active location in the weather scope application.
 * This interface defines operations to retrieve and update the currently active location.
 */
interface ActiveLocationRepository {
	/**
	 * Observes the currently active location as a Flow.
	 *
	 * @return A Flow emitting the current [ActiveLocation].
	 */
	fun observeActiveLocation(): Flow<ActiveLocation>

	/**
	 * Sets the specified location as the active location.
	 *
	 * @param location The [ActiveLocation] to set as active.
	 */
	suspend fun setActiveLocation(location: ActiveLocation)
}