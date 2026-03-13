package com.axehai.weatherscope.data.repository

import com.axehai.weatherscope.domain.model.ActiveLocation
import com.axehai.weatherscope.domain.repository.ActiveLocationRepository
import kotlinx.coroutines.flow.Flow

class ActiveLocationRepositoryImpl  : ActiveLocationRepository {
	override suspend fun getActiveLocationOrNull(): ActiveLocation? {
		TODO("Not yet implemented")
	}

	override fun observeActiveLocation(): Flow<ActiveLocation> {
		TODO("Not yet implemented")
	}

	override suspend fun setActiveLocation(location: ActiveLocation) {
		TODO("Not yet implemented")
	}
}