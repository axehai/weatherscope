package com.axehai.weatherscope.domain.use_case.set_active_location

import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import javax.inject.Inject

/**
 * Use case for setting the active location.
 * This class encapsulates the logic to set a new active location by delegating to the repository.
 */
class SetActiveLocationUseCase @Inject constructor(
	private val repository: ActiveLocationRepository
) {
	/**
	 * Sets the active location.
	 * @param activeLocation The active location to set.
	 */
	suspend operator fun invoke (activeLocation: ActiveLocation){
		repository.setActiveLocation(activeLocation)
	}
}