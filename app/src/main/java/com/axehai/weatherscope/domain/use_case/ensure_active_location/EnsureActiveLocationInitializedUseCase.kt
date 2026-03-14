package com.axehai.weatherscope.domain.use_case.ensure_active_location

import com.axehai.weatherscope.domain.defaults.ActiveLocationDefaults
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import javax.inject.Inject

/**
 * Use case responsible for ensuring that an active location is initialized in the application.
 * If no active location is currently set, it defaults to New Delhi.
 *
 * @property activeLocationRepository The repository used to access and modify the active location.
 */
class EnsureActiveLocationInitializedUseCase @Inject constructor(
	private val activeLocationRepository: ActiveLocationRepository
) {
	/**
	 * Invokes the use case to check and initialize the active location if necessary.
	 * This function is suspendable and should be called from a coroutine context.
	 */
	suspend operator fun invoke() {
		if (activeLocationRepository.getActiveLocationOrNull() == null) {
			activeLocationRepository.setActiveLocation(ActiveLocationDefaults.NEW_DELHI)
		}
	}
}